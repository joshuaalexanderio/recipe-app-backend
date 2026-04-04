package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.*;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.TodoistService;
import dev.joshalexander.recipeappbackend.service.TodoistShoppingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Proxy layer between Angular frontend and Todoist REST API.
 * The frontend never calls Todoist directly — it goes through here.
 */
@RestController
@RequestMapping("/api/todoist")
public class TodoistController {

    private final TodoistService todoistService;
    private final TodoistShoppingListService shoppingListService;
    private final UserRepository userRepository;

    public TodoistController(TodoistService todoistService,
                             TodoistShoppingListService shoppingListService,
                             UserRepository userRepository) {
        this.todoistService = todoistService;
        this.shoppingListService = shoppingListService;
        this.userRepository = userRepository;
    }

    // ── Projects ────────────────────────────────────────────────────

    @GetMapping("/projects")
    public ResponseEntity<List<TodoistProjectDTO>> getProjects() {
        return ResponseEntity.ok(todoistService.getProjects(getCurrentUserId()));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<TodoistProjectDTO> getProject(@PathVariable String projectId) {
        return ResponseEntity.ok(todoistService.getProject(getCurrentUserId(), projectId));
    }

    // ── Tasks ───────────────────────────────────────────────────────

    @GetMapping("/tasks")
    public ResponseEntity<List<TodoistTaskDTO>> getTasks(
            @RequestParam(required = false) String projectId) {
        return ResponseEntity.ok(todoistService.getTasks(getCurrentUserId(), projectId));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TodoistTaskDTO> getTask(@PathVariable String taskId) {
        return ResponseEntity.ok(todoistService.getTask(getCurrentUserId(), taskId));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TodoistTaskDTO> createTask(@RequestBody TodoistTaskCreateDTO request) {
        return ResponseEntity.ok(todoistService.createTask(getCurrentUserId(), request));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TodoistTaskDTO> updateTask(
            @PathVariable String taskId,
            @RequestBody TodoistTaskUpdateDTO request) {
        return ResponseEntity.ok(todoistService.updateTask(getCurrentUserId(), taskId, request));
    }

    @PostMapping("/tasks/{taskId}/close")
    public ResponseEntity<Void> closeTask(@PathVariable String taskId) {
        todoistService.closeTask(getCurrentUserId(), taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tasks/{taskId}/reopen")
    public ResponseEntity<Void> reopenTask(@PathVariable String taskId) {
        todoistService.reopenTask(getCurrentUserId(), taskId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        todoistService.deleteTask(getCurrentUserId(), taskId);
        return ResponseEntity.noContent().build();
    }

    // ── Shopping List ───────────────────────────────────────────────

    /**
     * Pushes selected recipe ingredients to a Todoist project as tasks.
     * If no projectId is provided, defaults to a "Shopping List" project.
     */
    @PostMapping("/shopping-list")
    public ResponseEntity<TodoistShoppingListResponseDTO> sendToShoppingList(
            @RequestBody TodoistShoppingListRequestDTO request) {
        return ResponseEntity.ok(
                shoppingListService.sendIngredientsToTodoist(getCurrentUserId(), request));
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("No user found for username: " + username))
                .getId();
    }
}