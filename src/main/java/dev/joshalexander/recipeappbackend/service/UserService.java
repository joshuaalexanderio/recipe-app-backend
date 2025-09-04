package dev.joshalexander.recipeappbackend.service;

import dev.joshalexander.recipeappbackend.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
}