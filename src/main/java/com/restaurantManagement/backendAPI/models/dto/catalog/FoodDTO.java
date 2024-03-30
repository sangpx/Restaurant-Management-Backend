package com.restaurantManagement.backendAPI.models.dto.catalog;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
    private String categoryName;
}
