package dev.joshalexander.recipeappbackend.dto;

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

    private Optional<List<IngredientUpdateDTO>> ingredients = Optional.empty();


    public void setName(String name) {
        this.name = name != null ? Optional.of(name) : Optional.empty();
    }

    public void setDescription(String description) {
        this.description = description != null ? Optional.of(description) : Optional.empty();
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl != null ? Optional.of(recipeUrl) : Optional.empty();
    }

    public void setIngredients(List<IngredientUpdateDTO> ingredients) {
        this.ingredients = ingredients != null ? Optional.of(ingredients) : Optional.empty();
    }
}