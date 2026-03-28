package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.config.TodoistProperties;
import dev.joshalexander.recipeappbackend.dto.*;
import dev.joshalexander.recipeappbackend.service.TodoistOAuthService;
import dev.joshalexander.recipeappbackend.service.TodoistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TodoistServiceImpl implements TodoistService {

  private static final Logger log = LoggerFactory.getLogger(TodoistServiceImpl.class);

  private final RestClient restClient;
  private final TodoistOAuthService oauthService;

  public TodoistServiceImpl(RestClient todoistRestClient,
      TodoistOAuthService oauthService) {
    this.restClient = todoistRestClient;
    this.oauthService = oauthService;
  }

  // ── Projects ────────────────────────────────────────────────────

  @Override
  public List<TodoistProjectDTO> getProjects(Long userId) {
    TodoistPaginatedResponseDTO<TodoistProjectDTO> response = restClient.get()
        .uri(TodoistProperties.API_BASE_URL + "/projects")
        .header("Authorization", bearer(userId))
        .retrieve()
        .body(new ParameterizedTypeReference<>() {
        });

    return response != null ? response.results() : List.of();
  }

  @Override
  public TodoistProjectDTO getProject(Long userId, String projectId) {
    return restClient.get()
        .uri(TodoistProperties.API_BASE_URL + "/projects/{id}", projectId)
        .header("Authorization", bearer(userId))
        .retrieve()
        .body(TodoistProjectDTO.class);
  }

  @Override
  public TodoistProjectDTO createProject(Long userId, String name) {
    return restClient.post()
        .uri(TodoistProperties.API_BASE_URL + "/projects")
        .header("Authorization", bearer(userId))
        .contentType(MediaType.APPLICATION_JSON)
        .body(java.util.Map.of("name", name))
        .retrieve()
        .body(TodoistProjectDTO.class);
  }

  // ── Tasks ───────────────────────────────────────────────────────

  @Override
  public List<TodoistTaskDTO> getTasks(Long userId, String projectId) {
    String uri = projectId != null
        ? TodoistProperties.API_BASE_URL + "/tasks?project_id=" + projectId
        : TodoistProperties.API_BASE_URL + "/tasks";

    TodoistPaginatedResponseDTO<TodoistTaskDTO> response = restClient.get()
        .uri(uri)
        .header("Authorization", bearer(userId))
        .retrieve()
        .body(new ParameterizedTypeReference<>() {
        });

    return response != null ? response.results() : List.of();
  }

  @Override
  public TodoistTaskDTO getTask(Long userId, String taskId) {
    return restClient.get()
        .uri(TodoistProperties.API_BASE_URL + "/tasks/{id}", taskId)
        .header("Authorization", bearer(userId))
        .retrieve()
        .body(TodoistTaskDTO.class);
  }

  @Override
  public TodoistTaskDTO createTask(Long userId, TodoistTaskCreateDTO request) {
    return restClient.post()
        .uri(TodoistProperties.API_BASE_URL + "/tasks")
        .header("Authorization", bearer(userId))
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(TodoistTaskDTO.class);
  }

  @Override
  public TodoistTaskDTO updateTask(Long userId, String taskId, TodoistTaskUpdateDTO request) {
    return restClient.post()
        .uri(TodoistProperties.API_BASE_URL + "/tasks/{id}", taskId)
        .header("Authorization", bearer(userId))
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(TodoistTaskDTO.class);
  }

  @Override
  public void closeTask(Long userId, String taskId) {
    restClient.post()
        .uri(TodoistProperties.API_BASE_URL + "/tasks/{id}/close", taskId)
        .header("Authorization", bearer(userId))
        .retrieve()
        .toBodilessEntity();
  }

  @Override
  public void reopenTask(Long userId, String taskId) {
    restClient.post()
        .uri(TodoistProperties.API_BASE_URL + "/tasks/{id}/reopen", taskId)
        .header("Authorization", bearer(userId))
        .retrieve()
        .toBodilessEntity();
  }

  @Override
  public void deleteTask(Long userId, String taskId) {
    restClient.delete()
        .uri(TodoistProperties.API_BASE_URL + "/tasks/{id}", taskId)
        .header("Authorization", bearer(userId))
        .retrieve()
        .toBodilessEntity();
  }

  // ── Private ─────────────────────────────────────────────────────

  /**
   * Builds the Bearer token header value for a given user.
   */
  private String bearer(Long userId) {
    return "Bearer " + oauthService.getAccessToken(userId);
  }
}