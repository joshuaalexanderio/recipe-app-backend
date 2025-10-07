package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.RecipeImportDTO;
import dev.joshalexander.recipeappbackend.service.RecipeImportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/recipeImport")
public class RecipeImportController {
    private static final Logger log = LoggerFactory.getLogger(RecipeImportController.class);
    private final RecipeImportService recipeImportService;

    public RecipeImportController(RecipeImportService recipeImportService) {
        this.recipeImportService = recipeImportService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importRecipe(@RequestParam String recipeURL) {
        try {
            RecipeImportDTO recipe = recipeImportService.getAIResponse(recipeURL);
            return ResponseEntity.ok(recipe);
        } catch (Exception e) {
            log.error("Error importing recipe", e);
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
