package dev.joshalexander.recipeappbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ApiRootController {

    @GetMapping("/")
    public Map<String, Object> getApiRoot() {
        return Map.of(
                "message", "Recipe App API",
                "version", "1.0.0",
                "endpoints", List.of(
                        "GET /api/users - List all users",
                        "GET /api/users/{id} - Get user by ID",
                        "GET /api/recipes - List all recipes (when implemented)",
                        "GET /api/recipes/{id} - Get recipe by ID (when implemented)",
                        "GET /api/ingredients - List all ingredients (when implemented)",
                        "GET /api/ingredients/{id} - Get ingredient by ID (when implemented)"
                ),
                "_links", Map.of(
                        "self", Map.of("href", "/"),
                        "users", Map.of("href", "/api/users"),
                        "recipes", Map.of("href", "/api/recipes"),
                        "ingredients", Map.of("href", "/api/ingredients")
                )
        );
    }
}