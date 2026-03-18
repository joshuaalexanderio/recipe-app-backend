package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.TodoistShoppingListRequestDTO;
import dev.joshalexander.recipeappbackend.dto.TodoistShoppingListResponseDTO;

/**
 * Pushes selected recipe ingredients to a Todoist project as tasks.
 */
public interface TodoistShoppingListService {

    /**
     * Sends the selected ingredients from a recipe to Todoist.
     * If no projectId is provided, finds or creates a "Shopping List" project.
     *
     * @param userId  the currently authenticated user
     * @param request contains recipeId, selected ingredientIds, and optional projectId
     * @return summary of created tasks
     */
    TodoistShoppingListResponseDTO sendIngredientsToTodoist(Long userId, TodoistShoppingListRequestDTO request);
}