package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps the JSON Todoist returns after a successful token exchange:
 * { "access_token": "...", "token_type": "Bearer" }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TodoistTokenResponseDTO(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType
) {
}