package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.repository.CategoryRepository;
import com.restaurantManagement.backendAPI.services.CategoryService;
import com.restaurantManagement.backendAPI.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
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
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category add(Category category) {
        Category categoryCreate = new Category();
        categoryCreate.setName(category.getName());
        categoryCreate.setCreatedAt(Date.from(Instant.now()));
        categoryCreate.setUpdatedAt(Date.from(Instant.now()));
        return categoryRepository.save(categoryCreate);
    }

    @Override
    public Category update(Category category, Long id) {
        Category categoryExisted = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Category with: " + id));
        categoryExisted.setName(category.getName());
        categoryExisted.setUpdatedAt(Date.from(Instant.now()));
        return categoryRepository.save(categoryExisted);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Category with: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public Category getDetail(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Category with: " + id));
        return category;
    }

    @Override
    public List<Category> searchCategory(String query) {
        List<Category> categories = categoryRepository.searchCategories(query);
        return categories;
    }

    @Override
    public Page<Category> getCategorysWithPaginationAndSorting(int pageNumber, int pageSize, String filed) {
        Page<Category> categoryPage = categoryRepository.findAll(
                PageRequest.of(pageNumber, pageSize)
                        .withSort(Sort.by(filed)));
        return categoryPage;
    }

    @Override
    public List<Category> getAlls() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
