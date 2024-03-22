package com.restaurantManagement.backendAPI.models.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private boolean status;
    private Long categoryId;
    private String categoryName;
}