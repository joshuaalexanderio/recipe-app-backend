package dev.joshalexander.recipeappbackend.dto;

/**
 * Lightweight response sent to the Angular frontend
 * so it knows whether the current user has connected Todoist.
 */
public record TodoistConnectionStatusDTO(
        boolean connected,
        String message
) {
    public static TodoistConnectionStatusDTO success() {
        return new TodoistConnectionStatusDTO(true, "Todoist account connected successfully.");
    }

    public static TodoistConnectionStatusDTO notConnected() {
        return new TodoistConnectionStatusDTO(false, "Todoist account is not connected.");
    }
}