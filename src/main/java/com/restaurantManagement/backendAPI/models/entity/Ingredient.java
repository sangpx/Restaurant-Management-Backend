package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private Double price;
    private String unit;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<ImportDetail> importDetailList;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<IngredientFood> ingredientFoodList;
}
