package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.RecipeImportDTO;

public interface RecipeImportService {
    RecipeImportDTO getAIResponse(String recipeURL);
}
