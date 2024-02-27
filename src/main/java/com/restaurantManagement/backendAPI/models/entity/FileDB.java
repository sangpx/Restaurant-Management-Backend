package com.restaurantManagement.backendAPI.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDB {
    private String name;
    private String url;
}
