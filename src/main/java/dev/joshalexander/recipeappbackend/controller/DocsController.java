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
                "users", Map.of(
                        "GET /users", "List all users",
                        "GET /users/{id}", "Get user by ID"
                ),
                "recipes", Map.of(
                        "GET /recipes", "List all recipes",
                        "GET /recipes/{id}", "Get recipe by ID",
                        "POST /recipes", "Create new recipe",
                        "PUT /recipes/{id}", "Update recipe (partial updates supported)"
                ),
                "ingredients", Map.of(
                        "GET /ingredients", "List all ingredients",
                        "GET /ingredients/{id}", "Get ingredient by ID"
                )
        ));

        // Ingredients for use in updateRecipe example - LinkedHashMap to allow null values
        Map<String, Object> ingredient1 = new LinkedHashMap<>();
        ingredient1.put("id", 1);
        ingredient1.put("name", "large egg");
        ingredient1.put("quantity", "5");
        ingredient1.put("unit", null);
        ingredient1.put("orderIndex", 1);

        Map<String, Object> ingredient2 = new LinkedHashMap<>();
        ingredient2.put("name", "cheese");
        ingredient2.put("quantity", "2");
        ingredient2.put("unit", "slices");
        ingredient2.put("orderIndex", 2);

        response.put("examples", Map.of(
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
                ),
                "updateRecipe", Map.of(
                        "method", "PUT",
                        "url", "/recipes/{id}",
                        "headers", Map.of("Content-Type", "application/json"),
                        "description", "Update recipe fields. Only include fields you want to change. NOTE: If you include 'ingredients', it will REPLACE the entire ingredients list.",
                        "examples", Map.of(
                                "updateNameOnly", Map.of(
                                        "description", "Update only the recipe name - ingredients preserved",
                                        "body", Map.of("name", "Updated Recipe Name")
                                ),
                                "updateNameAndDescription", Map.of(
                                        "description", "Update name and description - ingredients preserved",
                                        "body", Map.of(
                                                "name", "New Recipe Name",
                                                "description", "Updated description"
                                        )
                                ),
                                "replaceAllIngredients", Map.of(
                                        "description", "REPLACES entire ingredients list with these 2 items",
                                        "body", Map.of(
                                                "ingredients", List.of(ingredient1, ingredient2)
                                        )
                                ),
                                "updateNameAndReplaceIngredients", Map.of(
                                        "description", "Update name AND replace entire ingredients list",
                                        "body", Map.of(
                                                "name", "Complete Recipe Update",
                                                "ingredients", List.of(
                                                        Map.of("name", "flour", "quantity", "2", "unit", "cups", "orderIndex", 1)
                                                )
                                        )
                                )
                        )
                )
        ));

        return response;
    }
}