package dev.joshalexander.recipeappbackend.dto;

import java.util.List;

/**
 * Request body sent from Angular when the user checks
 * specific ingredients and hits "Send to Todoist".
 */
public record TodoistShoppingListRequestDTO(
        Long recipeId,
        List<Long> ingredientIds,
        /** Optional — if null, defaults to the "Shopping List" project. */
        String projectId
) {
}