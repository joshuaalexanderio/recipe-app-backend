package dev.joshalexander.recipeappbackend.service.impl;

import dev.joshalexander.recipeappbackend.dto.UserCreateDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.entity.User;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import dev.joshalexander.recipeappbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User newUser = userMapper.toUser(userCreateDTO);
        userRepository.save(newUser);
        return userMapper.toUserDTO(newUser);
    }
}