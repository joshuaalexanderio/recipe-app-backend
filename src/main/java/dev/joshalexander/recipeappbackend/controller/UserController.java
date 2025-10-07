package dev.joshalexander.recipeappbackend.controller;

import dev.joshalexander.recipeappbackend.dto.UserCreateDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserDTO user = userService.createUser(userCreateDTO);

        return ResponseEntity.ok(user);
    }
}