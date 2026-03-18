package dev.joshalexander.recipeappbackend.service;

/**
 * Handles the Todoist OAuth2 authorization flow:
 * building the authorize URL, exchanging codes for tokens,
 * and managing connection state.
 */
public interface TodoistOAuthService {

    /**
     * Generates the URL to redirect the user to Todoist's consent page.
     *
     * @param userId the currently authenticated user in your app
     * @return full redirect URL including client_id, scope, and state
     */
    String buildAuthorizeUrl(Long userId);

    /**
     * Called when Todoist redirects back with ?code=…&state=…
     * Validates state, exchanges code for token, and persists it.
     *
     * @param code  the authorization code from Todoist
     * @param state the CSRF state we originally sent
     * @return the userId that initiated the flow
     * @throws IllegalStateException if state is invalid or token exchange fails
     */
    Long handleCallback(String code, String state);

    /**
     * Checks whether a user already has a Todoist connection.
     */
    boolean isConnected(Long userId);

    /**
     * Retrieves the stored access token for a user.
     *
     * @throws IllegalStateException if no connection exists
     */
    String getAccessToken(Long userId);

    /**
     * Removes the stored Todoist token for a user.
     */
    void disconnect(Long userId);
}