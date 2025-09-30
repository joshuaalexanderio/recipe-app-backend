package dev.joshalexander.recipeappbackend.dto;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^https?://[^\\s]+$", message = "Recipe URL must be a valid HTTP/HTTPS URL")
    private String recipeUrl;
    private UserDTO user;
    private boolean favorite;
    private List<RecipeIngredientDTO> recipeIngredients;
}

