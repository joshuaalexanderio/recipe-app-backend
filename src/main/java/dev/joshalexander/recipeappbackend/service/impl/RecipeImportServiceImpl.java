package dev.joshalexander.recipeappbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import dev.joshalexander.recipeappbackend.dto.RecipeImportDTO;
import dev.joshalexander.recipeappbackend.exception.RecipePageFetchException;
import dev.joshalexander.recipeappbackend.service.RecipeImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import java.util.Arrays;

@Service
public class RecipeImportServiceImpl implements RecipeImportService {

  private static final Logger log = LoggerFactory.getLogger(RecipeImportServiceImpl.class);

  private final ObjectMapper objectMapper;
  private final RestClient restClient;

  public RecipeImportServiceImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.restClient = RestClient.builder()
        .defaultHeader("User-Agent", "Mozilla/5.0 (compatible; RecipeImporter/1.0)")
        .build();
  }

  @Override
  public RecipeImportDTO getAIResponse(String recipeURL) {
    log.info("Importing recipe from URL: {}", recipeURL);

    boolean isYoutube = recipeURL.contains("youtube.com") || recipeURL.contains("youtu.be");

    String prompt = isYoutube
        ? buildYoutubePrompt(recipeURL)
        : buildStaticPrompt(recipeURL, fetchPageText(recipeURL));

    String schemaJson = """
        {
          "type": "object",
          "properties": {
            "name":        { "type": "string" },
            "description": { "type": "string" },
            "recipeUrl":   { "type": "string" },
            "recipeIngredients": {
              "type": "array",
              "items": {
                "type": "object",
                "properties": {
                  "name":       { "type": "string" },
                  "quantity":   { "type": "string" },
                  "unit":       { "type": "string" },
                  "orderIndex": { "type": "integer" }
                },
                "required": ["name", "quantity", "unit", "orderIndex"]
              }
            }
          },
          "required": ["name", "description", "recipeUrl", "recipeIngredients"]
        }
        """;

    Schema schema = Schema.fromJson(schemaJson);

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .candidateCount(1)
        .responseSchema(schema)
        .build();

    Client client = new Client();
    GenerateContentResponse response;

    if (isYoutube) {
      Part videoPart = Part.fromUri(recipeURL, "video/mp4");
      Part textPart = Part.fromText(prompt);
      Content contents = Content.builder()
          .parts(Arrays.asList(videoPart, textPart))
          .build();
      response = client.models.generateContent("models/gemini-2.5-flash", contents, config);
    } else {
      response = client.models.generateContent("models/gemini-2.5-flash", prompt, config);
    }

    String jsonResponse = response.text();
    log.info("AI response: {}", jsonResponse);

    try {
      RecipeImportDTO recipe = objectMapper.readValue(jsonResponse, RecipeImportDTO.class);
      log.info("Successfully parsed recipe: {}", recipe.getName());
      return recipe;
    } catch (Exception e) {
      log.error("Failed to parse AI response. Raw response: {}", jsonResponse, e);
      throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
    }
  }

  // --- Prompt builders ---

  private String buildStaticPrompt(String recipeURL, String pageText) {
    return """
        You are a precise recipe data extractor.
        
        Below is the raw text content scraped from the recipe page at: %s
        
        ===== PAGE CONTENT START =====
        %s
        ===== PAGE CONTENT END =====
        
        Extract the recipe ingredients EXACTLY as they appear in the page content above.
        
        RULES — follow without exception:
        - Extract ONLY ingredients explicitly present in the page content above.
          Do NOT use your training knowledge to infer, guess, or add any ingredient
          not found in the text.
        - The recipe may have multiple sections (e.g. "Crepe:", "Sauce:", "Assembly:").
          Extract ALL ingredients from ALL sections without skipping any.
        - Each ingredient entry maps to exactly one line item from the source.
          Do NOT merge multiple ingredients, and do NOT split one into multiple entries.
        - Do NOT duplicate any ingredient. Every line item appears exactly once.
        - Separate quantity and unit from the ingredient name.
          Example: "4 cups all-purpose flour" → name: "all-purpose flour", quantity: "4", unit: "cups"
        - Abbreviate units: tablespoon→tbsp, teaspoon→tsp, ounce→oz, pound→lb,
          milliliter→mL, gram→g, kilogram→kg. Keep "cup", "bunch", "package", "clove" as-is.
        - If no standard unit applies, use any size descriptor as the unit.
          Example: "5 large eggs"   → name: "eggs",   quantity: "5", unit: "large"
          Example: "1 medium onion" → name: "onion",  quantity: "1", unit: "medium"
          Example: "2 whole cloves" → name: "cloves", quantity: "2", unit: "whole"
        - If no quantity applies (e.g. "neutral oil for frying"), set quantity to "to taste" and unit to "".
        - Strip brand names and parenthetical notes from ingredient names.
        - orderIndex must be a sequential integer starting at 1, following top-to-bottom
          order of appearance in the page content.
        - description must be 500 characters or fewer and describe the dish, not the steps.
        - Set recipeUrl to exactly: %s
        """.formatted(recipeURL, pageText, recipeURL);
  }

  private String buildYoutubePrompt(String recipeURL) {
    return """
         You are a precise recipe data extractor analyzing a cooking video.
        
        Watch the YouTube video at: %s
        
         Extract every ingredient shown on screen or spoken aloud, in the order they appear.
        
         RULES — follow without exception:
         - Extract ONLY ingredients explicitly shown or spoken. No training knowledge.
         - All sections and components must be included (e.g. "Batter:", "Sauce:", "For serving:").
         - Each entry maps to exactly one ingredient. No merging, splitting, or duplicates.
         - Separate quantity and unit from name.
         - Abbreviate units: tablespoon→tbsp, teaspoon→tsp, ounce→oz, pound→lb,
           milliliter→mL, gram→g, kilogram→kg. Keep "cup", "bunch", "package", "clove" as-is.
        - If no standard unit applies, use any size descriptor as the unit.
            Example: "5 large eggs"  → name: "eggs",  quantity: "5", unit: "large"
            Example: "1 medium onion" → name: "onion", quantity: "1", unit: "medium"
            Example: "2 whole cloves" → name: "cloves", quantity: "2", unit: "whole"       \s
        - If no quantity applies (e.g. "neutral oil for frying"), set quantity to "to taste" and unit to "".
         - No quantity → set quantity to "to taste", unit to "".
         - orderIndex: sequential integer from 1, in order of appearance.
         - description: 500 chars max, describes the dish not the steps.
         - Set recipeUrl to exactly: %s
        """.formatted(recipeURL, recipeURL);
  }

  // --- Page fetcher ---

  /**
   * Fetches the page and strips HTML tags, leaving only visible text. Passing raw text (not HTML)
   * to the model reduces noise and token waste.
   */
  private String fetchPageText(String url) {
    String html;

    try {
      ResponseEntity<String> response = restClient.get()
          .uri(url)
          .retrieve()
          .toEntity(String.class);

      int statusCode = response.getStatusCode().value();
      if (statusCode == 401 || statusCode == 403 || statusCode == 429) {
        log.warn("Site blocked scraping with HTTP {}: {}", statusCode, url);
        throw new RecipePageFetchException(
            RecipePageFetchException.Reason.BLOCKED, url,
            "The site at '%s' blocked access (HTTP %d). Try a supported recipe site."
                .formatted(url, statusCode)
        );
      }

      html = response.getBody();

    } catch (RecipePageFetchException e) {
      throw e;
    } catch (RestClientResponseException e) {
      int statusCode = e.getStatusCode().value();
      if (statusCode == 401 || statusCode == 403 || statusCode == 429) {
        log.warn("Site blocked scraping with HTTP {}: {}", statusCode, url);
        throw new RecipePageFetchException(
            RecipePageFetchException.Reason.BLOCKED, url,
            "The site at '%s' blocked access (HTTP %d). Try a supported recipe site."
                .formatted(url, statusCode)
        );
      }
      log.error("HTTP error fetching page: {} - {}", url, e.getMessage());
      throw new RecipePageFetchException(
          RecipePageFetchException.Reason.NETWORK_ERROR, url,
          "Could not reach '%s'. Check the URL and try again.".formatted(url), e
      );
    } catch (Exception e) {
      log.error("Network error fetching page: {}", url, e);
      throw new RecipePageFetchException(
          RecipePageFetchException.Reason.NETWORK_ERROR, url,
          "Could not reach '%s'. Check the URL and try again.".formatted(url), e
      );
    }

    if (html == null || html.isBlank()) {
      log.warn("Empty body returned from: {}", url);
      throw new RecipePageFetchException(
          RecipePageFetchException.Reason.EMPTY, url,
          "No content was returned from '%s'. The page may require a login or JavaScript to load."
              .formatted(url)
      );
    }

    String text = html
        .replaceAll("<script[^>]*>[\\s\\S]*?</script>", "")
        .replaceAll("<style[^>]*>[\\s\\S]*?</style>", "")
        .replaceAll("<[^>]+>", " ")
        .replaceAll("&amp;", "&")
        .replaceAll("&nbsp;", " ")
        .replaceAll("&lt;", "<")
        .replaceAll("&gt;", ">")
        .replaceAll("\\s{2,}", " ")
        .trim();

    if (text.length() > 12_000) {
      text = text.substring(0, 12_000);
    }

    log.info("Fetched and stripped page text ({} chars) from: {}", text.length(), url);
    return text;
  }
}