package dev.joshalexander.recipeappbackend.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class RecipeUpdateDTO {

    private Optional<String> name = Optional.empty();

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private Optional<String> description = Optional.empty();

    private Optional<String> recipeUrl = Optional.empty();

    private Optional<List<RecipeIngredientUpdateDTO>> recipeIngredients = Optional.empty();

    private Optional<Boolean> favorite = Optional.empty();


    public void setName(String name) {
        this.name = name != null ? Optional.of(name) : Optional.empty();
    }

    public void setDescription(String description) {
        this.description = description != null ? Optional.of(description) : Optional.empty();
    }

    @Pattern(regexp = "^https?://[^\\s]+$", message = "Recipe URL must be a valid HTTP/HTTPS URL")
    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl != null ? Optional.of(recipeUrl) : Optional.empty();
    }

    public void setRecipeIngredients(List<RecipeIngredientUpdateDTO> recipeIngredients) {
        this.recipeIngredients = recipeIngredients != null ? Optional.of(recipeIngredients) : Optional.empty();
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite != null ? Optional.of(favorite) : Optional.empty();
    }

}