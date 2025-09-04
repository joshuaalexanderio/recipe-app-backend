package dev.joshalexander.recipeappbackend.mapper;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    IngredientDTO toIngredientDTO(Ingredient ingredient);
    Ingredient toIngredient(IngredientDTO ingredientDTO);
    UserDTO toUserDTO(User user);
    RecipeDTO toRecipeDTO(Recipe recipe);

}
