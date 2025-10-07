package dev.joshalexander.recipeappbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> getApiRoot() {
        return Map.of(
                "title", "Recipe App API",
                "version", "1.0.0",
                "_links", Map.of(
                        "self", Map.of("href", "/"),
                        "docs", Map.of("href", "/docs"),
                        "recipes", Map.of("href", "/api/recipes"),
                        "users", Map.of("href", "/api/users"),
                        "ingredients", Map.of("href", "/api/ingredients"),
                        "recipeIngredients", Map.of("href", "/api/recipeIngredients"),
                        "recipeImport", Map.of("href", "/api/recipeImport")
                )
        );
    }
}