package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {
}
