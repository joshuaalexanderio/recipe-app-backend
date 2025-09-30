package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeDTO> getAllRecipes();

    Optional<RecipeDTO> getRecipeById(Long id);

    RecipeDTO createRecipe(RecipeDTO recipeDTO);

    RecipeDTO updateRecipe(Long id, RecipeUpdateDTO updateDTO);

    void deleteAllRecipes();

}

