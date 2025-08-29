package dev.joshalexander.recipeappbackend.controller;
import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final EntityMapper ingredientMapper;
    private final IngredientRepository ingredientRepository;

    public IngredientController(EntityMapper ingredientMapper, IngredientRepository ingredientRepository) {
        this.ingredientMapper = ingredientMapper;
        this.ingredientRepository = ingredientRepository;
    }
    @GetMapping
    public List<IngredientDTO> getAllUsers() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toIngredientDTO)
                .toList();
    }
}
