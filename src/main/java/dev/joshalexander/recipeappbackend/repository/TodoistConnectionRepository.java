package dev.joshalexander.recipeappbackend.repository;

import dev.joshalexander.recipeappbackend.entity.TodoistConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoistConnectionRepository extends JpaRepository<TodoistConnection, Long> {

    Optional<TodoistConnection> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}