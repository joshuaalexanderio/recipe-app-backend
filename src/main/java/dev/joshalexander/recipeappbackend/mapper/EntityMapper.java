package dev.joshalexander.recipeappbackend.mapper;

import dev.joshalexander.recipeappbackend.dto.*;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import dev.joshalexander.recipeappbackend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    IngredientCreateDTO toIngredientCreateDTO(Ingredient ingredient);

    Ingredient toIngredient(IngredientCreateDTO ingredientCreateDTO);

    IngredientDTO toIngredientDTO(Ingredient ingredient);

    UserDTO toUserDTO(User user);

    RecipeDTO toRecipeDTO(Recipe recipe);

    Recipe toRecipe(RecipeDTO recipeDTO);

    RecipeIngredientDTO toRecipeIngredientDTO(RecipeIngredient recipeIngredient);

    RecipeIngredient toRecipeIngredient(RecipeIngredientDTO recipeIngredientDTO);

    RecipeIngredient toRecipeIngredient(RecipeIngredientCreateDTO recipeIngredientCreateDTO);

}
