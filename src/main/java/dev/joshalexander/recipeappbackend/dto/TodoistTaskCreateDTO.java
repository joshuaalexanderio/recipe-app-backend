package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request body for creating a new task in Todoist.
 * Maps to the POST /rest/v2/tasks endpoint.
 */
public record TodoistTaskCreateDTO(
        String content,
        String description,
        @JsonProperty("project_id") String projectId,
        @JsonProperty("section_id") String sectionId,
        @JsonProperty("parent_id") String parentId,
        int order,
        List<String> labels,
        int priority,
        @JsonProperty("due_string") String dueString,
        @JsonProperty("due_date") String dueDate,
        @JsonProperty("due_datetime") String dueDatetime,
        @JsonProperty("due_lang") String dueLang
) {
}