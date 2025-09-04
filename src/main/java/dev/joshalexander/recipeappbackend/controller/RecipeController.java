package dev.joshalexander.recipeappbackend.controller;
import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<RecipeDTO> users = recipeService.getAllRecipes();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long recipeId) {
        Optional<RecipeDTO> user = recipeService.getRecipeById(recipeId);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}