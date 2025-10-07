package dev.joshalexander.recipeappbackend.service;
import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeImportResponseDTO;

public interface RecipeImportService {
    RecipeImportResponseDTO getAIResponse(String recipeURL);
}
