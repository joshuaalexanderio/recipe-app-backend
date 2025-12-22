package dev.joshalexander.recipeappbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeCreateDTO {
    @NotBlank
    private String name;
    private String description;
    @Pattern(regexp = "^https?://[^\\s]+$", message = "Recipe URL must be a valid HTTP/HTTPS URL")
    private String recipeUrl;
    private UserDTO user;
    private boolean favorite;
    private List<RecipeIngredientCreateDTO> recipeIngredients;
}

