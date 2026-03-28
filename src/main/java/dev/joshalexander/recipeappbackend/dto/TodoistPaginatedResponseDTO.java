package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wraps paginated responses from the Todoist API v1. Response format: { "results": [...],
 * "next_cursor": "..." }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoistPaginatedResponseDTO<T>(
    @JsonProperty("results") List<T> results,
    @JsonProperty("next_cursor") String nextCursor
) {

}