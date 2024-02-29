package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.RestaurantInfo;

public interface RestaurantInfoService {
    RestaurantInfo add(RestaurantInfo restaurantInfo);
    RestaurantInfo update(RestaurantInfo restaurantInfo, Long id);

}
