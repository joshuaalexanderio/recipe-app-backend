package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Maps a Todoist task from the REST API v2.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoistTaskDTO(
        String id,
        @JsonProperty("project_id") String projectId,
        @JsonProperty("section_id") String sectionId,
        String content,
        String description,
        @JsonProperty("is_completed") boolean completed,
        List<String> labels,
        @JsonProperty("parent_id") String parentId,
        int order,
        int priority,
        @JsonProperty("due") TodoistDueDTO due,
        String url,
        @JsonProperty("comment_count") int commentCount,
        @JsonProperty("creator_id") String creatorId
) {
    /**
     * Nested due-date object.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TodoistDueDTO(
            String string,
            String date,
            @JsonProperty("is_recurring") boolean recurring,
            String datetime,
            String timezone
    ) {
    }
}