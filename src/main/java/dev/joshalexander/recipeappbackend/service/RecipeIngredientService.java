package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.RecipeIngredientCreateDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeIngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeIngredientUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientService {
    List<RecipeIngredientDTO> getAllRecipeIngredients(Long recipeId);

    Optional<RecipeIngredientDTO> getRecipeIngredientById(Long id);

    RecipeIngredientDTO createRecipeIngredient(RecipeIngredientCreateDTO recipeIngredientCreateDTO);

    Optional<RecipeIngredientDTO> deleteRecipeIngredient(Long id);
}
