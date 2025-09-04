package dev.joshalexander.recipeappbackend.service.impl;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final EntityMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(EntityMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }
}