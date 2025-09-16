package dev.joshalexander.recipeappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String quantity;

    private String defaultUnit;

    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}