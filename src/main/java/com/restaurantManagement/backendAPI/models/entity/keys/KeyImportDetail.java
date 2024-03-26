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
public class KeyImportDetail implements Serializable {
    @Column(name = "import_id")
    private Long importId;
    @Column(name = "ingredient_id")
    private Long ingredientId;
}
