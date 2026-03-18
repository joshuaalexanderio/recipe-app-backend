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
    private static final String DEFAULT_PROJECT_NAME = "Shopping List";

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
        // 1. Resolve the target Todoist project
        String projectId = request.projectId();
        String projectName;

        if (projectId == null || projectId.isBlank()) {
            TodoistProjectDTO project = findOrCreateShoppingListProject(userId);
            projectId = project.id();
            projectName = project.name();
        } else {
            TodoistProjectDTO project = todoistService.getProject(userId, projectId);
            projectName = project.name();
        }

        // 2. Look up the selected ingredients
        List<RecipeIngredient> ingredients = recipeIngredientRepo.findAllById(request.ingredientIds());

        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("No ingredients found for the provided IDs.");
        }

        // 3. Create a Todoist task for each ingredient
        List<String> taskIds = new ArrayList<>();

        for (RecipeIngredient ingredient : ingredients) {
            String taskContent = formatIngredient(ingredient);

            TodoistTaskCreateDTO taskRequest = new TodoistTaskCreateDTO(
                    taskContent,       // content
                    null,              // description
                    projectId,         // projectId
                    null,              // sectionId
                    null,              // parentId
                    0,                 // order
                    null,              // labels
                    1,                 // priority (1 = normal)
                    null,              // dueString
                    null,              // dueDate
                    null,              // dueDatetime
                    null               // dueLang
            );

            TodoistTaskDTO created = todoistService.createTask(userId, taskRequest);
            taskIds.add(created.id());
        }

        log.info("Sent {} ingredients to Todoist project '{}' for userId={}",
                taskIds.size(), projectName, userId);

        return new TodoistShoppingListResponseDTO(projectId, projectName, taskIds.size(), taskIds);
    }

    // ── Private helpers ─────────────────────────────────────────────

    /**
     * Finds the user's "Shopping List" project in Todoist, or creates one.
     */
    private TodoistProjectDTO findOrCreateShoppingListProject(Long userId) {
        List<TodoistProjectDTO> projects = todoistService.getProjects(userId);

        return projects.stream()
                .filter(p -> DEFAULT_PROJECT_NAME.equalsIgnoreCase(p.name()))
                .findFirst()
                .orElseGet(() -> createShoppingListProject(userId));
    }

    /**
     * Creates a new "Shopping List" project via the Todoist API.
     * Uses RestClient directly since TodoistService only wraps task endpoints.
     */
    private TodoistProjectDTO createShoppingListProject(Long userId) {
        log.info("Creating '{}' project in Todoist for userId={}", DEFAULT_PROJECT_NAME, userId);

        // We call through TodoistService — but we need a project create method.
        // For now, we'll add it to the TodoistService interface.
        return todoistService.createProject(userId, DEFAULT_PROJECT_NAME);
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