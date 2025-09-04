package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.service.IngredientService;
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
    public Optional<IngredientDTO> getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredientMapper::toIngredientDTO);
    }
}
