package dev.joshalexander.recipeappbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DocsController {

    @GetMapping("/docs")
    public Map<String, Object> getDocs() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("title", "Recipe App API Documentation");
        response.put("version", "1.0.0");
        response.put("baseUrl", "http://localhost:8080/api");
        response.put("endpoints", Map.of(
                "title", "Recipe App API Documentation",
                "version", "1.0.0",
                "baseUrl", "http://localhost:8080/api",
                "endpoints", Map.of(
                        "users", Map.of(
                                "GET /users", "List all users",
                                "GET /users/{id}", "Get user by ID"
                        ),
                        "recipes", Map.of(
                                "GET /recipes", "List all recipes",
                                "GET /recipes/{id}", "Get recipe by ID",
                                "POST /recipes", "Create new recipe"
                        ),
                        "ingredients", Map.of(
                                "GET /ingredients", "List all ingredients",
                                "GET /ingredients/{id}", "Get ingredient by ID"
                        )
                ),
                "examples", Map.of(
                        "createRecipe", Map.of(
                                "method", "POST",
                                "url", "/recipes",
                                "headers", Map.of("Content-Type", "application/json"),
                                "body", Map.of(
                                        "name", "Simple pasta",
                                        "description", "Basic pasta with sauce",
                                        "user", Map.of("id", 1),
                                        "ingredients", List.of(
                                                Map.of("name", "pasta", "quantity", "1", "unit", "lb", "orderIndex", 1),
                                                Map.of("name", "marinara sauce", "quantity", "2", "unit", "cups", "orderIndex", 2),
                                                Map.of("name", "parmesan", "quantity", "1/2", "unit", "cup", "orderIndex", 3)
                                        )
                                )
                        )
                )

        ));
        return response;
    }
}