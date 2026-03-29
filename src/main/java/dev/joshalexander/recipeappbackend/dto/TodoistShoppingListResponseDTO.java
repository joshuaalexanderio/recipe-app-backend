package dev.joshalexander.recipeappbackend.dto;

import java.util.List;

/**
 * Response returned after pushing ingredients to Todoist.
 */
public record TodoistShoppingListResponseDTO(
        String projectId,
        int taskCount,
        List<String> taskIds
) {
}