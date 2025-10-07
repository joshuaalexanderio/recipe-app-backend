package dev.joshalexander.recipeappbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import dev.joshalexander.recipeappbackend.dto.RecipeImportResponseDTO;
import dev.joshalexander.recipeappbackend.service.RecipeImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList;
import com.google.genai.types.Schema;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecipeImportServiceImpl implements RecipeImportService {
    private static final Logger log = LoggerFactory.getLogger(RecipeImportServiceImpl.class);
    private final ObjectMapper objectMapper;

    public RecipeImportServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RecipeImportResponseDTO getAIResponse(String recipeURL) {
        log.info("Importing recipe from URL: {}", recipeURL);

        // Json schema for response
        String schemaJson = """
        {
          "type": "object",
          "properties": {
            "name": {
              "type": "string"
            },
            "description": {
              "type": "string"
            },
            "recipeUrl": {
              "type": "string"
            },
            "recipeIngredients": {
              "type": "array",
              "items": {
                "type": "object",
                "properties": {
                  "name": {
                    "type": "string"
                  },
                  "quantity": {
                    "type": "string"
                  },
                  "unit": {
                    "type": "string"
                  },
                  "orderIndex": {
                    "type": "integer"
                  }
                },
                "required": ["name", "quantity", "unit", "orderIndex"]
              }
            }
          },
          "required": ["name", "description", "recipeUrl", "recipeIngredients"]
        }
        """;

        // Build response schema from json
        Schema schema = Schema.fromJson(schemaJson);

        // Set the response schema in GenerateContentConfig
        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .candidateCount(1)
                .responseSchema(schema)
                .build();

        String prompt = """
                Extract recipe from %s for shopping list import.
                - Description max 500 chars
                - Separate ingredient names from quantities (e.g., "flour" not "2 cups flour")
                - Abbreviate long units (tbsp, tsp, oz, etc)
                - Use URL: %s
        """.formatted(recipeURL, recipeURL);

        Client client = new Client();
        GenerateContentResponse response;

        // Handle YouTube URLs
        if (recipeURL.contains("youtube.com") || recipeURL.contains("youtu.be")) {
            log.info("Detected YouTube URL. Preparing multimodal request with video.");

            // Create the Parts of the prompt
            Part videoPart = Part.fromUri(
                    recipeURL,
                    "video/mp4"
            );
            Part textPart = Part.fromText(prompt);
            List<Part> contentParts = Arrays.asList(videoPart, textPart);

            // Create the Content object from parts
            Content contents = Content.builder()
                    .parts(contentParts)
                    .build();

            response = client.models.generateContent(
                    "models/gemini-2.5-flash",
                    contents,
                    config
            );
        } else {
            log.info("Detected standard URL. Preparing text-only request.");

            response = client.models.generateContent(
                    "models/gemini-2.5-flash",
                    prompt,
                    config
            );
        }
        String jsonResponse = response.text();
        log.info("AI  {}", jsonResponse);

        try {
            RecipeImportResponseDTO recipe = objectMapper.readValue(jsonResponse, RecipeImportResponseDTO.class);
            log.info("Successfully parsed recipe: {}", recipe.getName());
            return recipe;
        } catch (Exception e) {
            log.error("Failed to parse AI response", e);
            log.error("Raw response was: {}", jsonResponse);
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }
}