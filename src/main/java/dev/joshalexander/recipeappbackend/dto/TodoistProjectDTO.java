package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps a Todoist project from the REST API v2.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoistProjectDTO(
        String id,
        String name,
        String color,
        @JsonProperty("parent_id") String parentId,
        int order,
        @JsonProperty("comment_count") int commentCount,
        @JsonProperty("is_shared") boolean shared,
        @JsonProperty("is_favorite") boolean favorite,
        @JsonProperty("is_inbox_project") boolean inboxProject,
        @JsonProperty("view_style") String viewStyle,
        String url
) {
}