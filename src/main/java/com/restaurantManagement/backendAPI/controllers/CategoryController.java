package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.dto.payload.response.PageResult;
import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.services.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoriesPaging")
    public PageResult<Page<Category>> getCategoriesPaging(
            @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String filed){
        Page<Category> categoryPages = categoryService.getCategorysWithPaginationAndSorting(pageNumber, pageSize, filed);
        return new PageResult<>(categoryPages.getSize(), categoryPages);
    }

    @GetMapping("/getDetailCategory/{id}")
    public ResponseEntity<Category> getDetailCategory(@PathVariable("id") Long id){
        Category category = categoryService.getDetail(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@RequestParam String name, @RequestParam MultipartFile file){
        Category createCategory = new Category();
        createCategory.setName(name);
        Category savedCategory = categoryService.add(createCategory, file);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@RequestParam String name,@PathVariable Long id,@RequestParam MultipartFile file){
        Category updateCategory = new Category();
        updateCategory.setName(name);
        Category savedCategory = categoryService.update(updateCategory, id, file);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok("Category deleted successfully!.");
    }

    @GetMapping("/searchCategories")
    public ResponseEntity<List<Category>> searchCategories(
            @RequestParam("query") String query){
        return ResponseEntity.ok(categoryService.searchCategory(query));
    }
}
