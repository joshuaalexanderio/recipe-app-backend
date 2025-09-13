package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeUpdateDTO;
import dev.joshalexander.recipeappbackend.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long recipeId) {
        Optional<RecipeDTO> recipe = recipeService.getRecipeById(recipeId);

        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(
            @PathVariable Long id,
            @RequestBody RecipeUpdateDTO updateDTO) {

        RecipeDTO updatedRecipe = recipeService.updateRecipe(id, updateDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

}