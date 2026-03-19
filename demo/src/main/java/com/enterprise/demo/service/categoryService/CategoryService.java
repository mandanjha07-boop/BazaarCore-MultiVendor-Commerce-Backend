package com.enterprise.demo.service.categoryService;

import com.enterprise.demo.dto.category.CategoryRequest;
import com.enterprise.demo.dto.category.CategoryResponse;
import com.enterprise.demo.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id,CategoryRequest request);

    void deleteCategoryById(Long id);

    void deleteAllCategories();
}
