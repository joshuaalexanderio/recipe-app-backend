package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.*;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import dev.joshalexander.recipeappbackend.entity.User;
import dev.joshalexander.recipeappbackend.exception.IngredientNotFoundException;
import dev.joshalexander.recipeappbackend.exception.RecipeNotFoundException;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.repository.RecipeRepository;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.RecipeService;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final EntityMapper recipeMapper;
    private final UserRepository userRepository;

    public RecipeServiceImpl(EntityMapper recipeMapper, RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.recipeMapper = recipeMapper;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toRecipeDTO)
                .toList();
    }

    @Override
    public Optional<RecipeDTO> getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toRecipeDTO);
    }

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setRecipeUrl(recipeDTO.getRecipeUrl());

        // Find existing user by ID
        User user = userRepository.findById(recipeDTO.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User with ID " +
                        recipeDTO.getUser().getId() + " not found"));
        recipe.setUser(user);
        for (RecipeIngredientDTO recipeIngredientDTO : recipeDTO.getRecipeIngredients()) {
            // Find existing Ingredient or create a new one
            String ingredientName = findOrCreateIngredientByName(recipeIngredientDTO);

            // Grab ingredient
            Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(ingredientName)
                    .orElseThrow(() -> new IngredientNotFoundException("Ingredient with name '" + ingredientName + "' not found"));

            // Create RecipeIngredient attached to the Ingredient
            RecipeIngredient recipeIngredient = getRecipeIngredient(recipeIngredientDTO, ingredient, recipe);
            recipe.getRecipeIngredients().add(recipeIngredient);
        }
        // Save, cascade will handle RecipeIngredients
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.toRecipeDTO(savedRecipe);
    }

    private static @NotNull RecipeIngredient getRecipeIngredient(RecipeIngredientDTO recipeIngredientDTO, Ingredient ingredient, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setName(ingredient.getName());
        recipeIngredient.setUnit(recipeIngredientDTO.getUnit());
        recipeIngredient.setQuantity(recipeIngredientDTO.getQuantity());
        recipeIngredient.setOrderIndex(recipeIngredientDTO.getOrderIndex());
        recipeIngredient.setIngredient(ingredient);

        // Set RecipeIngredient:Recipe relationship
        recipeIngredient.setRecipe(recipe);
        return recipeIngredient;
    }


    // If exists, return existing ingredient name, else create ingredient and return name of new ingredient
    private String findOrCreateIngredientByName(RecipeIngredientDTO recipeIngredientDTO) {
        Optional<String> existingIngredientName = ingredientRepository.findDistinctNameIgnoreCase(recipeIngredientDTO.getName().trim());
        if (existingIngredientName.isPresent()) {
            return existingIngredientName.get();
        } else {
            // Create the ingredient with name normalized to lowercase
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(recipeIngredientDTO.getName().toLowerCase());
            newIngredient.setDefaultUnit(recipeIngredientDTO.getUnit());
            Ingredient savedIngredient = ingredientRepository.save(newIngredient);
            return savedIngredient.getName();
        }
    }

    @Transactional
    public RecipeDTO updateRecipe(Long id, RecipeUpdateDTO updateDTO) {

        // Get recipe from repository
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with id " + id + " not found"));

        // Update fields in existingRecipe if present in updateDTO
        updateDTO.getName().ifPresent(existingRecipe::setName);
        updateDTO.getDescription().ifPresent(existingRecipe::setDescription);
        updateDTO.getRecipeUrl().ifPresent(existingRecipe::setRecipeUrl);
        updateDTO.getRecipeIngredients().ifPresent(recipeIngredientUpdates ->
                updateRecipeIngredients(existingRecipe, recipeIngredientUpdates));

        // Save updated recipe
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.toRecipeDTO(savedRecipe);
    }

    private void updateRecipeIngredients(Recipe recipe, List<RecipeIngredientUpdateDTO> recipeIngredientUpdates) {
        recipe.getRecipeIngredients().clear();

        for (RecipeIngredientUpdateDTO updateDTO : recipeIngredientUpdates) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();

            updateDTO.getId().ifPresent(recipeIngredient::setId);
            updateDTO.getName().ifPresent(recipeIngredient::setName);
            updateDTO.getQuantity().ifPresent(recipeIngredient::setQuantity);
            updateDTO.getUnit().ifPresent(recipeIngredient::setUnit);
            updateDTO.getOrderIndex().ifPresent(recipeIngredient::setOrderIndex);

            // Set the recipe relationship
            recipeIngredient.setRecipe(recipe);
            recipe.getRecipeIngredients().add(recipeIngredient);
        }
    }

    @Override
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }
}