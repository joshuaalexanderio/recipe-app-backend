package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.TodoistConnectionStatusDTO;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.TodoistOAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Handles the three legs of the Todoist OAuth2 flow: 1. GET  /api/auth/todoist/authorize   →
 * redirect user to Todoist 2. GET  /api/auth/todoist/callback     → Todoist redirects back here 3.
 * GET  /api/auth/todoist/status       → is the user connected? 4. POST /api/auth/todoist/disconnect
 * → revoke the connection
 */
@RestController
@RequestMapping("/api/auth/todoist")
public class TodoistOAuthController {

  private static final Logger log = LoggerFactory.getLogger(TodoistOAuthController.class);

  private final TodoistOAuthService oauthService;
  private final UserRepository userRepository;

  /**
   * Where to send the user after a successful OAuth callback (Angular app URL).
   */
  @Value("${todoist.frontend-callback-url:https://joshalexander.dev/settings/integrations}")
  private String frontendCallbackUrl;

  public TodoistOAuthController(TodoistOAuthService oauthService, UserRepository userRepository) {
    this.oauthService = oauthService;
    this.userRepository = userRepository;
  }

  // ── 1. Initiate ─────────────────────────────────────────────────

  @GetMapping("/authorize")
  public void authorize(HttpServletResponse response) throws IOException {
    Long userId = getCurrentUserId();
    String authorizeUrl = oauthService.buildAuthorizeUrl(userId);
    response.sendRedirect(authorizeUrl);
  }

  // ── 2. Callback ─────────────────────────────────────────────────

  /**
   * Todoist redirects here after the user grants (or denies) access. We exchange the code for a
   * token, persist it, and redirect the user back to the Angular frontend. NOTE: Register this URL
   * in the Todoist App Management Console as your OAuth Redirect URL, e.g.
   * https://yourapi.com/api/auth/todoist/callback
   */
  @GetMapping("/callback")
  public void callback(@RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "state", required = false) String state,
      @RequestParam(value = "error", required = false) String error,
      HttpServletResponse response) throws IOException {

    if (error != null) {
      log.warn("Todoist OAuth denied: {}", error);
      response.sendRedirect(frontendCallbackUrl + "?todoist=error&reason=" + error);
      return;
    }

    try {
      oauthService.handleCallback(code, state);
      response.sendRedirect(frontendCallbackUrl + "?todoist=success");
    } catch (IllegalStateException ex) {
      log.error("Todoist OAuth callback failed", ex);
      response.sendRedirect(frontendCallbackUrl + "?todoist=error&reason=state_mismatch");
    }
  }

  // ── 3. Status ───────────────────────────────────────────────────

  @GetMapping("/status")
  public ResponseEntity<TodoistConnectionStatusDTO> status() {
    Long userId = getCurrentUserId();
    boolean connected = oauthService.isConnected(userId);
    return ResponseEntity.ok(
        connected
            ? TodoistConnectionStatusDTO.success()
            : TodoistConnectionStatusDTO.notConnected()
    );
  }

  // ── 4. Disconnect ───────────────────────────────────────────────

  @PostMapping("/disconnect")
  public ResponseEntity<TodoistConnectionStatusDTO> disconnect() {
    Long userId = getCurrentUserId();
    oauthService.disconnect(userId);
    return ResponseEntity.ok(TodoistConnectionStatusDTO.notConnected());
  }

  // ── Auth helper ──────────────────────────────────────────────────

  private Long getCurrentUserId() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("No user found for username: " + username))
        .getId();
  }
}