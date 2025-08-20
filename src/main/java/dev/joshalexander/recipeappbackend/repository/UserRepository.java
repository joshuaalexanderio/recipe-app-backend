package dev.joshalexander.recipeappbackend.repository;

import dev.joshalexander.recipeappbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
