package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getCategoriesByParent(Category parent);

    @Query(value = "SELECT * FROM category c where c.category_id = c.parent_id", nativeQuery = true)
    List<Category> findAllParentCategory();
}
