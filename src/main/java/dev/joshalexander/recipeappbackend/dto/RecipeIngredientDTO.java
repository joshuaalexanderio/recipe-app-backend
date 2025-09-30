package dev.joshalexander.recipeappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String unit;
    private Integer orderIndex;
}

