package dev.joshalexander.recipeappbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientCreateDTO {
    @NotBlank
    private String name;
    private String quantity;
    private String unit;
    private Integer orderIndex;
}

