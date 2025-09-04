package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<IngredientDTO> getAllIngredients();
    Optional<IngredientDTO> getIngredientById(Long id);
}
