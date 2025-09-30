package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long ingredientId) {
        Optional<IngredientDTO> ingredient = ingredientService.getIngredientById(ingredientId);

        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientData) {
        IngredientDTO createdIngredient = ingredientService.createIngredient(ingredientData);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
    }
}
