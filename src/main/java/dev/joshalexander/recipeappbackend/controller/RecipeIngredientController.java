package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.RecipeIngredientDTO;
import dev.joshalexander.recipeappbackend.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipeIngredients")
public class RecipeIngredientController {

  private final RecipeIngredientService recipeIngredientService;

  public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
    this.recipeIngredientService = recipeIngredientService;
  }

  @GetMapping("/{recipeId}")
  public ResponseEntity<List<RecipeIngredientDTO>> getAllRecipeIngredients(
      @PathVariable Long recipeId) {
    List<RecipeIngredientDTO> recipeIngredients = recipeIngredientService.getAllRecipeIngredients(
        recipeId);
    return ResponseEntity.ok(recipeIngredients);
  }

  @DeleteMapping("/{recipeIngredientId}")
  public ResponseEntity<Void> deleteRecipeIngredient(@PathVariable Long recipeIngredientId) {
    this.recipeIngredientService.deleteRecipeIngredient(recipeIngredientId);
    return ResponseEntity.noContent().build();
  }
}
