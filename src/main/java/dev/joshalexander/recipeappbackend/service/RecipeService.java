package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.RecipeDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeDTO> getAllRecipes();
    Optional<RecipeDTO> getRecipeById(Long id);
}