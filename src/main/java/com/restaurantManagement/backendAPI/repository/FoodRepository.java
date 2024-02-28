package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
