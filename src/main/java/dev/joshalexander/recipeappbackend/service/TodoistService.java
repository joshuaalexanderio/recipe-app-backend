package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.*;

import java.util.List;

/**
 * Wraps the Todoist REST API v2 for project and task operations.
 * All methods require a valid Todoist connection for the given userId.
 */
public interface TodoistService {

    // ── Projects ────────────────────────────────────────────────────

    List<TodoistProjectDTO> getProjects(Long userId);

    TodoistProjectDTO getProject(Long userId, String projectId);

    TodoistProjectDTO createProject(Long userId, String name);

    // ── Tasks ───────────────────────────────────────────────────────

    List<TodoistTaskDTO> getTasks(Long userId, String projectId);

    TodoistTaskDTO getTask(Long userId, String taskId);

    TodoistTaskDTO createTask(Long userId, TodoistTaskCreateDTO request);

    TodoistTaskDTO updateTask(Long userId, String taskId, TodoistTaskUpdateDTO request);

    void closeTask(Long userId, String taskId);

    void reopenTask(Long userId, String taskId);

    void deleteTask(Long userId, String taskId);
}