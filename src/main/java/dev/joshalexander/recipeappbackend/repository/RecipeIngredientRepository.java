package dev.joshalexander.recipeappbackend.repository;

import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    @Query
    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
}


