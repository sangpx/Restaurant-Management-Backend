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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoriesPaging")
    public PageResult<Page<Category>> getCategoriesPaging(
            @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String filed){
        Page<Category> categoryPages = categoryService.getCategorysWithPaginationAndSorting(pageNumber, pageSize, filed);
        return new PageResult<>(categoryPages.getSize(), categoryPages);
    }

    @GetMapping("/getAlls")
    public ResponseEntity<List<Category>> getAlls(){
        List<Category> categorys = categoryService.getAlls();
        return ResponseEntity.ok(categorys);
    }

    @GetMapping("/getDetailCategory/{id}")
    public ResponseEntity<Category> getDetailCategory(@PathVariable("id") Long id){
        Category category = categoryService.getDetail(id);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.add(category), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable Long id){
        return new ResponseEntity<>(categoryService.update(category, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok("xóa loại món ăn thành công!");
    }

    @GetMapping("/searchCategories")
    public ResponseEntity<List<Category>> searchCategories(
            @RequestParam("query") String query){
        return ResponseEntity.ok(categoryService.searchCategory(query));
    }
}
