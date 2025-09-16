package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.IngredientUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<IngredientDTO> getAllIngredients();
    Optional<IngredientDTO> getIngredientById(Long id);
    IngredientDTO createIngredient(IngredientDTO ingredientDTO);
    IngredientDTO updateIngredient(Long id, IngredientUpdateDTO updateDTO);
    Optional<IngredientDTO> deleteIngredient(Long id);
}
