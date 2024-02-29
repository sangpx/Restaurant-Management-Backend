package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.repository.CategoryRepository;
import com.restaurantManagement.backendAPI.services.CategoryService;
import com.restaurantManagement.backendAPI.services.FileStorageService;
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

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Category add(Category category, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.save(file);
            // Thiết lập đường dẫn ảnh cho category
            category.setImage(imageUrl);
            category.setCreatedAt(Date.from(Instant.now()));
            category.setUpdatedAt(Date.from(Instant.now()));
        }
        Category createCategory = categoryRepository.save(category);
        return createCategory;
    }

    @Override
    public Category update(Category category, Long id, MultipartFile file) {
        Category categoryFind = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Category with: " + id));
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.save(file);
            // Thiết lập đường dẫn ảnh cho category
            categoryFind.setImage(imageUrl);
        }
        categoryFind.setName(category.getName());
        categoryFind.setUpdatedAt(Date.from(Instant.now()));
        Category updateCategory = categoryRepository.save(categoryFind);
        return updateCategory;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Category with: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public Category getDetail(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Category with: " + id));
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
}
