package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.models.entity.Food;
import com.restaurantManagement.backendAPI.repository.FoodRepository;
import com.restaurantManagement.backendAPI.services.FileStorageService;
import com.restaurantManagement.backendAPI.services.FoodService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Food add(Food food, Long categoryId) {
        Food foodCreate = new Food();
        Category category = new Category();
        category.setId(categoryId);
        foodCreate.setCategory(category);
        foodCreate.setName(food.getName());
        foodCreate.setDescription(food.getDescription());
        foodCreate.setPrice(food.getPrice());
        foodCreate.setCreatedAt(Date.from(Instant.now()));
        foodCreate.setUpdatedAt(Date.from(Instant.now()));
        return foodRepository.save(foodCreate);
    }

    @Override
    public Food update(Food food, Long id, Long categoryId) {
        Food foodExist = foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin món ăn!"));
        Category category = new Category();
        category.setId(categoryId);
        foodExist.setCategory(category);
        foodExist.setName(food.getName());
        foodExist.setDescription(food.getDescription());
        foodExist.setPrice(food.getPrice());
        foodExist.setUpdatedAt(Date.from(Instant.now()));
        return foodRepository.save(foodExist);
    }


    @Override
    public void delete(Long id) {
        Food foodExist = foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin món ăn!"));
        foodRepository.delete(foodExist);
    }

    @Override
    public Food getDetail(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin món ăn!"));
    }

    @Override
    public List<Food> searchFood(String query) {
        List<Food> foods = foodRepository.searchFoods(query);
        return foods;
    }

    @Override
    public Page<FoodDTO> getFoodsWithPaginationAndSorting(int pageNumber, int pageSize, String filed) {
        //DTO -> Entity
        Page<Food> foodPage = foodRepository.findAll(PageRequest.of(pageNumber, pageSize)
                .withSort(Sort.by(filed)));
        //Entity -> DTO
        Page<FoodDTO> foodDTOPage = foodPage.map(food ->
                modelMapper.map(food, FoodDTO.class));
        return foodDTOPage;
    }

    @Override
    public List<FoodDTO> getAlls() {
        List<Food> listFood = foodRepository.findAll();
        List<FoodDTO> foodDTOList = listFood.stream()
                .map(food -> modelMapper.map(food, FoodDTO.class))
                .collect(Collectors.toList());
        return foodDTOList;
    }

    @Override
    public long countFoods() {
        return foodRepository.count();
    }
}
