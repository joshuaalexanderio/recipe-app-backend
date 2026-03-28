package dev.joshalexander.recipeappbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Binds to todoist.* keys in application.yml. Activate with
 *
 * @EnableConfigurationProperties(TodoistProperties.class) or @ConfigurationPropertiesScan on your
 * main class.
 */
@ConfigurationProperties(prefix = "app.todoist")
public record TodoistProperties(
    String clientId,
    String clientSecret,
    String redirectUri,
    String scope
) {

  // ── Todoist OAuth endpoints (constants) ──────────────────────────
  public static final String AUTHORIZE_URL = "https://todoist.com/oauth/authorize";
  public static final String TOKEN_URL = "https://todoist.com/oauth/access_token";
  public static final String API_BASE_URL = "https://api.todoist.com/api/v1";
}