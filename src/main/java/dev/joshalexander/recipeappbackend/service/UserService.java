package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.UserCreateDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO createUser(UserCreateDTO userCreateDTO);
}