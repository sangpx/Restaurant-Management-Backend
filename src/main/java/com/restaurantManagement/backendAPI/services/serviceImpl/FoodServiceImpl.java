package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.dto.catalog.DeskDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.models.entity.Desk;
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

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Food add(Food food, Long categoryId, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.save(file);
            // Thiết lập đường dẫn ảnh cho category
            food.setImage(imageUrl);
            food.setStatus(true);
            Category category = new Category();
            category.setId(categoryId);
            food.setCategory(category);
            food.setCreatedAt(Date.from(Instant.now()));
            food.setUpdatedAt(Date.from(Instant.now()));
        }
        Food createdFood = foodRepository.save(food);
        return createdFood;
    }

    @Override
    public Food update(Food food, Long id, MultipartFile file, Long categoryId) {
        Food foodExist = foodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Food with: " + id));
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.save(file);
            // Thiết lập đường dẫn ảnh cho category
            foodExist.setImage(imageUrl);
        }
        foodExist.setStatus(!food.isStatus());
        foodExist.setName(food.getName());
        foodExist.setPrice(food.getPrice());
        foodExist.setDescription(food.getDescription());
        Category category = new Category();
        category.setId(categoryId);
        foodExist.setCategory(category);
        foodExist.setUpdatedAt(Date.from(Instant.now()));
        return foodRepository.save(foodExist);
    }

    @Override
    public void delete(Long id) {
        Food foodExist = foodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Food with: " + id));
        foodRepository.delete(foodExist);
    }

    @Override
    public Food getDetail(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Food with: " + id));
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
}
