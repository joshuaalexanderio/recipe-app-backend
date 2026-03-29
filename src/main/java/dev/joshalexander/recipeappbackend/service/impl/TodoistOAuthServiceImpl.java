package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.config.TodoistProperties;
import dev.joshalexander.recipeappbackend.dto.TodoistTokenResponseDTO;
import dev.joshalexander.recipeappbackend.entity.TodoistConnection;
import dev.joshalexander.recipeappbackend.repository.TodoistConnectionRepository;
import dev.joshalexander.recipeappbackend.service.TodoistOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TodoistOAuthServiceImpl implements TodoistOAuthService {

  private static final Logger log = LoggerFactory.getLogger(TodoistOAuthServiceImpl.class);

  private final TodoistProperties props;
  private final RestClient restClient;
  private final TodoistConnectionRepository connectionRepo;

  /**
   * In-memory store for CSRF state tokens. For production, move this to Redis or a DB table with
   * TTL. Key = state string, Value = userId that initiated the flow.
   */
  private final Map<String, Long> pendingStates = new ConcurrentHashMap<>();

  public TodoistOAuthServiceImpl(TodoistProperties props,
      RestClient todoistRestClient,
      TodoistConnectionRepository connectionRepo) {
    this.props = props;
    this.restClient = todoistRestClient;
    this.connectionRepo = connectionRepo;
  }

  // ── Step 1: Build the Todoist authorization URL ──────────────────

  @Override
  public String buildAuthorizeUrl(Long userId) {
    String state = generateState();
    pendingStates.put(state, userId);
    log.info("Redirect URI: {}", props.redirectUri());
    return UriComponentsBuilder.fromUriString(TodoistProperties.AUTHORIZE_URL)
        .queryParam("client_id", props.clientId())
        .queryParam("scope", props.scope())
        .queryParam("state", state)
        .queryParam("redirect_uri", props.redirectUri())
        .toUriString();

  }

  // ── Step 2: Handle the callback & exchange code for token ────────

  @Override
  @Transactional
  public Long handleCallback(String code, String state) {
    // 1. Validate state
    Long userId = pendingStates.remove(state);
    if (userId == null) {
      throw new IllegalStateException(
          "Invalid or expired OAuth state. Please restart the connection flow.");
    }

    // 2. Exchange authorization code for access token
    TodoistTokenResponseDTO tokenResponse = exchangeCodeForToken(code);

    // 3. Persist (upsert) the token
    TodoistConnection connection = connectionRepo.findByUserId(userId)
        .orElseGet(() -> {
          TodoistConnection c = new TodoistConnection();
          c.setUserId(userId);
          return c;
        });
    connection.setAccessToken(tokenResponse.accessToken());
    connectionRepo.save(connection);

    log.info("Todoist connected for userId={}", userId);
    return userId;
  }

  // ── Helpers ──────────────────────────────────────────────────────

  @Override
  public boolean isConnected(Long userId) {
    return connectionRepo.existsByUserId(userId);
  }

  @Override
  public String getAccessToken(Long userId) {
    return connectionRepo.findByUserId(userId)
        .map(TodoistConnection::getAccessToken)
        .orElseThrow(() -> new IllegalStateException(
            "No Todoist connection for userId=" + userId));
  }

  @Override
  @Transactional
  public void disconnect(Long userId) {
    connectionRepo.deleteByUserId(userId);
    log.info("Todoist disconnected for userId={}", userId);
  }

  // ── Private ─────────────────────────────────────────────────────

  /**
   * POSTs to Todoist's token endpoint to exchange the authorization code. Todoist expects
   * form-encoded params: client_id, client_secret, code. Returns { "access_token": "…",
   * "token_type": "Bearer" }
   */
  private TodoistTokenResponseDTO exchangeCodeForToken(String code) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("client_id", props.clientId());
    formData.add("client_secret", props.clientSecret());
    formData.add("code", code);

    TodoistTokenResponseDTO response = restClient.post()
        .uri(TodoistProperties.TOKEN_URL)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(formData)
        .retrieve()
        .body(TodoistTokenResponseDTO.class);

    if (response == null || response.accessToken() == null) {
      throw new IllegalStateException("Todoist token exchange returned an empty response.");
    }
    return response;
  }

  /**
   * Generates a cryptographically random state parameter.
   */
  private String generateState() {
    byte[] bytes = new byte[32];
    new SecureRandom().nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }
}