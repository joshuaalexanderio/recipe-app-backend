package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeIngredientCreateDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeIngredientDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.RecipeIngredientRepository;
import dev.joshalexander.recipeappbackend.service.RecipeIngredientService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {
    private final EntityMapper entityMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientServiceImpl(EntityMapper entityMapper, RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<RecipeIngredientDTO> getAllRecipeIngredients(Long recipeId) {
        return recipeIngredientRepository.findByRecipe_Id(recipeId)
                .stream()
                .map(entityMapper::toRecipeIngredientDTO)
                .toList();
    }

    @Override
    public Optional<RecipeIngredientDTO> getRecipeIngredientById(Long id) {
        return recipeIngredientRepository.findById(id)
                .map(entityMapper::toRecipeIngredientDTO);
    }

    @Override
    public RecipeIngredientDTO createRecipeIngredient(RecipeIngredientCreateDTO recipeIngredientCreateDTO) {
        RecipeIngredient recipeIngredient = entityMapper.toRecipeIngredient(recipeIngredientCreateDTO);
        RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return entityMapper.toRecipeIngredientDTO(savedRecipeIngredient);
    }

    @Transactional
    public Optional<RecipeIngredientDTO> deleteRecipeIngredient(Long id) {
        Optional<RecipeIngredient> optionalRecipeIngredient = recipeIngredientRepository.findById(id);

        if (optionalRecipeIngredient.isPresent()) {
            RecipeIngredient recipeIngredient = optionalRecipeIngredient.get();
            RecipeIngredientDTO deletedRecipeIngredient = entityMapper.toRecipeIngredientDTO(recipeIngredient);
            recipeIngredientRepository.deleteById(id);
            return Optional.of(deletedRecipeIngredient);
        }
        return Optional.empty();
    }

}
