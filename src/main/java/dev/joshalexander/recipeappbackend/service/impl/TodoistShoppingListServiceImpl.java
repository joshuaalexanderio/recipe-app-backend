package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.*;
import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import dev.joshalexander.recipeappbackend.repository.RecipeIngredientRepository;
import dev.joshalexander.recipeappbackend.service.TodoistService;
import dev.joshalexander.recipeappbackend.service.TodoistShoppingListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoistShoppingListServiceImpl implements TodoistShoppingListService {

    private static final Logger log = LoggerFactory.getLogger(TodoistShoppingListServiceImpl.class);
    private final TodoistService todoistService;
    private final RecipeIngredientRepository recipeIngredientRepo;

    public TodoistShoppingListServiceImpl(TodoistService todoistService,
                                          RecipeIngredientRepository recipeIngredientRepo) {
        this.todoistService = todoistService;
        this.recipeIngredientRepo = recipeIngredientRepo;
    }

    @Override
    public TodoistShoppingListResponseDTO sendIngredientsToTodoist(Long userId,
                                                                   TodoistShoppingListRequestDTO request) {
        // 1. Resolve project — use provided projectId or fall back to inbox
        String projectId = request.projectId();
        if (projectId == null || projectId.isBlank()) {
            projectId = todoistService.getProjects(userId).stream()
                    .filter(TodoistProjectDTO::inboxProject)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No inbox project found"))
                    .id();
        }
        final String resolvedProjectId = projectId;

        // 2. Look up the selected ingredients
        List<RecipeIngredient> ingredients = recipeIngredientRepo.findAllById(request.ingredientIds());
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("No ingredients found for the provided IDs.");
        }

        // 3. Create a Todoist task for each ingredient
        List<String> taskIds = new ArrayList<>();
        for (RecipeIngredient ingredient : ingredients) {
            TodoistTaskDTO created = todoistService.createTask(userId, new TodoistTaskCreateDTO(
                    formatIngredient(ingredient), null, resolvedProjectId,
                    null, null, 0, null, 1, null, null, null, null
            ));
            taskIds.add(created.id());
        }

        log.info("Sent {} ingredients to Todoist project {} for userId={}", taskIds.size(), resolvedProjectId, userId);
        return new TodoistShoppingListResponseDTO(resolvedProjectId, taskIds.size(), taskIds);
    }

    /**
     * Formats an ingredient into a human-readable task string.
     * Examples: "2 cups flour", "1 lb chicken breast", "salt"
     */
    private String formatIngredient(RecipeIngredient ingredient) {
        StringBuilder sb = new StringBuilder();

        if (ingredient.getQuantity() != null && !ingredient.getQuantity().isBlank()) {
            sb.append(ingredient.getQuantity()).append(" ");
        }

        if (ingredient.getUnit() != null && !ingredient.getUnit().isBlank()) {
            sb.append(ingredient.getUnit()).append(" ");
        }

        sb.append(ingredient.getName());

        return sb.toString().trim();
    }
}