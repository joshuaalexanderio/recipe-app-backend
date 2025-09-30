package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeIngredientDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientService {
    List<RecipeIngredientDTO> getAllRecipeIngredients(Long recipeId);

    Optional<RecipeIngredientDTO> getRecipeIngredientById(Long id);
    
    RecipeIngredientDTO createRecipeIngredient(RecipeIngredientDTO recipeIngredientDTO);
}
