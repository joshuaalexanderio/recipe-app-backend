package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.IngredientCreateDTO;
import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.IngredientUpdateDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.exception.IngredientNotFoundException;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.service.IngredientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final EntityMapper ingredientMapper;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(EntityMapper ingredientMapper, IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toIngredientDTO)
                .toList();
    }

    @Override
    public Optional<IngredientDTO> getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredientMapper::toIngredientDTO);
    }

    @Override
    public IngredientDTO createIngredient(IngredientCreateDTO ingredientCreateDTO) {
        ingredientCreateDTO.setName(ingredientCreateDTO.getName().toLowerCase());
        Ingredient ingredient = ingredientMapper.toIngredient(ingredientCreateDTO);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toIngredientDTO(savedIngredient);
    }

    @Transactional
    public IngredientDTO updateIngredient(Long id, IngredientUpdateDTO updateDTO) {

        // Get ingredient from repository
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Update fields in existingIngredient if present in updateDTO
        updateDTO.getName().ifPresent(existingIngredient::setName);
        updateDTO.getDefaultUnit().ifPresent(existingIngredient::setDefaultUnit);

        // Save updated ingredient
        Ingredient savedIngredient = ingredientRepository.save(existingIngredient);
        return ingredientMapper.toIngredientDTO(savedIngredient);
    }

    @Transactional
    public Optional<IngredientDTO> deleteIngredient(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);

        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            IngredientDTO deletedIngredient = ingredientMapper.toIngredientDTO(ingredient);
            ingredientRepository.deleteById(id);
            return Optional.of(deletedIngredient);
        }
        return Optional.empty();

    }
}
