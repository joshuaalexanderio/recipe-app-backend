package dev.joshalexander.recipeappbackend.repository;

import dev.joshalexander.recipeappbackend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
