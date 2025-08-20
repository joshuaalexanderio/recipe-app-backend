package dev.joshalexander.recipeappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private String recipeUrl;
    private UserDTO user;
    private List<IngredientDTO> ingredients;
}


