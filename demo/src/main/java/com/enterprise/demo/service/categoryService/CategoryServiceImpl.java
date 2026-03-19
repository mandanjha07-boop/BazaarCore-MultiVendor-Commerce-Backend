package com.enterprise.demo.service.categoryService;

import com.enterprise.demo.dto.category.CategoryRequest;
import com.enterprise.demo.dto.category.CategoryResponse;
import com.enterprise.demo.entity.Category;
import com.enterprise.demo.mapper.CategoryMapper;
import com.enterprise.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category parentCategory =null;
        if(request.getParentCategoryId()!=null){
            parentCategory = categoryRepository.findById
                    (request.getParentCategoryId()).orElseThrow(()->new RuntimeException
                    ("No Parent Category Found by Given id"));
        }
        Category tobeSaved = CategoryMapper.toEntity(request,parentCategory);
        return CategoryMapper.toDto(categoryRepository.save(tobeSaved));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream().map(cat->CategoryMapper.toDto(cat)).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
         Category existing = categoryRepository.findById(id).orElseThrow(()->new RuntimeException
                ("No Parent Category Found by Given id"));
         return  CategoryMapper.toDto(existing);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        if (request.getParentCategoryId() != null
                && request.getParentCategoryId().equals(id)) {
            throw new RuntimeException("Category cannot be its own parent");
        }
        Category existing = categoryRepository.findById(id).orElseThrow(()->new RuntimeException
                ("No Parent Category Found by Given id"));
        if(request.getName()!=null){
            existing.setName(request.getName());
        }
        if(request.getDescription()!=null){
            existing.setDescription(request.getDescription());
        }
        if(request.getImageUrl()!=null){
            existing.setImageUrl(request.getImageUrl());
        }
        if(request.getIsActive()!=null){
            existing.setIsActive(request.getIsActive());
        }

        existing.setParentCategory(
                request.getParentCategoryId()!=null
                ?categoryRepository.findById(request.getParentCategoryId())
                        .orElseThrow(() -> new RuntimeException("Parent category not found"))
                        :existing.getParentCategory()
        );
//        if (request.getParentCategoryId() != null) {
//            Category parent = categoryRepository.findById(request.getParentCategoryId())
//                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
//
//            existing.setParentCategory(parent);
//        }

        return CategoryMapper.toDto(categoryRepository.save(existing));
    }



    @Override
    public void deleteCategoryById(Long id) {
        Category existing = categoryRepository.findById(id).orElseThrow(()->new RuntimeException
                ("Category Found by Given id"));
        Boolean hasChildren = categoryRepository.existsByParentCategoryId(id);
        if(hasChildren){
            throw new RuntimeException("Cannot delete category with subcategories");
        }
        categoryRepository.delete(existing);

    }

    @Override
    public void deleteAllCategories() {
      categoryRepository.deleteAll();
    }
}
