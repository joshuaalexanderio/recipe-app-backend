package dev.joshalexander.recipeappbackend.mapper;

import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.dto.RecipeDTO;
import dev.joshalexander.recipeappbackend.dto.UserDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    IngredientDTO toIngredientDTO(Ingredient ingredient);

    UserDTO toUserDTO(User user);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "ingredients", source = "ingredients")
    RecipeDTO toRecipeDTO(Recipe recipe);
}
