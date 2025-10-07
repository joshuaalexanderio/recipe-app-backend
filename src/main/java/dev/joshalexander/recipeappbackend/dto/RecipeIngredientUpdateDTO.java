package dev.joshalexander.recipeappbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
// Id passed through path
public class RecipeIngredientUpdateDTO {

    private Optional<String> name = Optional.empty();

    private Optional<String> quantity = Optional.empty();

    private Optional<Integer> orderIndex = Optional.empty();

    private Optional<String> unit = Optional.empty();

    public void setName(String name) {
        this.name = name != null ? Optional.of(name) : Optional.empty();
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity != null ? Optional.of(quantity) : Optional.empty();
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex != null ? Optional.of(orderIndex) : Optional.empty();
    }

    public void setUnit(String unit) {
        this.unit = unit != null ? Optional.of(unit) : Optional.empty();
    }


}