package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import com.restaurantManagement.backendAPI.models.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FoodService {
    Food add(Food food, Long categoryId, MultipartFile file);
    Food update(Food food, Long id, MultipartFile file, Long categoryId);
    void delete(Long id);
    Food getDetail(Long id);
    List<Food> searchFood(String query);
    Page<FoodDTO> getFoodsWithPaginationAndSorting(int pageNumber, int pageSize, String filed);

    List<FoodDTO> getAlls();
}
