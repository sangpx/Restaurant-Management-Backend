package com.restaurantManagement.backendAPI.models.dto.catalog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeskDTO {
    private Long id;
    private String name;
    private boolean status;
    private int quantitySeat;
    private Long floorId;
    private String floorName;
}
