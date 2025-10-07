package dev.joshalexander.recipeappbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
// Id passed through path
public class IngredientUpdateDTO {

    private Optional<String> name = Optional.empty();

    private Optional<String> defaultUnit = Optional.empty();

    public void setName(String name) {
        this.name = name != null ? Optional.of(name) : Optional.empty();
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit != null ? Optional.of(defaultUnit) : Optional.empty();
    }
}