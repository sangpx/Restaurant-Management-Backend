package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category add(Category category, MultipartFile file);
    Category update(Category category, Long id, MultipartFile file);
    void delete(Long id);
    Category getDetail(Long id);
    List<Category> searchCategory(String query);
    Page<Category> getCategorysWithPaginationAndSorting(int pageNumber, int pageSize, String filed);

    List<Category> getAlls();
}
