package com.restaurantManagement.backendAPI.models.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeyIngredientFood implements Serializable {
    @Column(name = "ingredient_id")
    private Long ingredientId;
    @Column(name = "food_id")
    private Long foodId;
}
