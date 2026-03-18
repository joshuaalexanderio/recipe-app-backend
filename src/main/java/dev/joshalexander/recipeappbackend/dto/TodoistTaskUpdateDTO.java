package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request body for updating an existing Todoist task.
 * Maps to the POST /rest/v2/tasks/{id} endpoint.
 */
public record TodoistTaskUpdateDTO(
        String content,
        String description,
        List<String> labels,
        int priority,
        @JsonProperty("due_string") String dueString,
        @JsonProperty("due_date") String dueDate,
        @JsonProperty("due_datetime") String dueDatetime,
        @JsonProperty("due_lang") String dueLang
) {
}