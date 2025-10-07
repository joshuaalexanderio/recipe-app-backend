package dev.joshalexander.recipeappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientCreateDTO {
    private String name;
    private String quantity;
    private String unit;
    private Integer orderIndex;
}

