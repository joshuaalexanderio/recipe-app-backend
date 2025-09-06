package dev.joshalexander.recipeappbackend.service.impl;
import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.User;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.repository.RecipeRepository;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.RecipeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        // Handle ingredients with normalized names
        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientDTO dto : recipeDTO.getIngredients()) {
            String normalizedName = findOrCreateIngredientName(dto.getName());

            Ingredient ingredient = new Ingredient();
            ingredient.setName(normalizedName);
            ingredient.setQuantity(dto.getQuantity());
            ingredient.setUnit(dto.getUnit());
            ingredient.setRecipe(recipe);
            ingredient.setOrderIndex(dto.getOrderIndex());
            ingredients.add(ingredient);
        }

        recipe.setIngredients(ingredients);

        // Save and convert back to DTO
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.toRecipeDTO(savedRecipe);
    }

    private String findOrCreateIngredientName(String inputName) {
        return ingredientRepository.findDistinctNameIgnoreCase(inputName.trim())
                .orElse(inputName.trim());
    }
}