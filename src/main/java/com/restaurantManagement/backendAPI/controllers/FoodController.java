package com.restaurantManagement.backendAPI.controllers;


import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import com.restaurantManagement.backendAPI.models.dto.payload.response.PageResult;
import com.restaurantManagement.backendAPI.models.entity.Food;
import com.restaurantManagement.backendAPI.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/getAlls")
    public ResponseEntity<List<FoodDTO>> getAlls(){
        List<FoodDTO> foodDTOList = foodService.getAlls();
        return ResponseEntity.ok(foodDTOList);
    }

    @GetMapping("/getFoodsPaging")
    public PageResult<Page<FoodDTO>> getFoodsPaging(
            @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String filed){
        Page<FoodDTO> foodDTOPages = foodService.getFoodsWithPaginationAndSorting(pageNumber, pageSize, filed);
        return new PageResult<>(foodDTOPages.getSize(), foodDTOPages);
    }

    @GetMapping("/getDetailFood/{id}")
    public ResponseEntity<Food> getDetailFood(@PathVariable("id") Long id){
        Food food = foodService.getDetail(id);
        return ResponseEntity.ok(food);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createFood")
    public ResponseEntity<Food> createFood(@RequestBody Food food, @RequestParam Long categoryId){
        return new ResponseEntity<>(foodService.add(food, categoryId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateFood/{id}")
    public ResponseEntity<Food> updateFood(@RequestBody Food food,
                                                @PathVariable Long id,
                                                @RequestParam Long categoryId){
        return new ResponseEntity<>(foodService.update(food, id, categoryId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable("id") Long id){
        foodService.delete(id);
        return ResponseEntity.ok("Xóa món ăn thành công!");
    }

    @GetMapping("/searchFoods")
    public ResponseEntity<List<Food>> searchFoods(
            @RequestParam("query") String query){
        return ResponseEntity.ok(foodService.searchFood(query));
    }

    @GetMapping("/countFood")
    public ResponseEntity<Long> getFoodCount() {
        return new ResponseEntity<>(foodService.countFoods(), HttpStatus.OK);
    }
}
