package com.enterprise.demo.repository;

import com.enterprise.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByParentCategoryId(Long parentId);
}
