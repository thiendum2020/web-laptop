package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Brand;
import com.java.weblaptop.entity.Category;
import com.java.weblaptop.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Productrepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> getProductsByCategory(Category category, Pageable pageable);
    List<Product> findProductByCategory(Category category);
    List<Product> findProductByBrand(Brand brand);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    List<Product> findAll(Specification<Product> spec);

    Page<Product> findByProductNameContaining(String name, Pageable pageable);
    Page<Product> findAllByCategoryAndBrand(Category category, Brand brand, Pageable pageable);
    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByBrand(Brand brand, Pageable pageable);
    Page<Product> findAllByProductNameContainingAndCategoryAndBrand(String name,Category category, Brand brand, Pageable pageable);
    Page<Product> findAllByProductNameContainingAndCategory(String name, Category category, Pageable pageable);
    Page<Product> findAllByProductNameContainingAndBrand(String name, Brand brand, Pageable pageable);

    Page<Product> findAllByOrderByProductPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByProductPriceDesc(Pageable pageable);
    Page<Product> findAllByOrderByRatingTBDesc(Pageable pageable);
    Page<Product> findAllByOrderByProductNameAsc(Pageable pageable);
    Page<Product> findAllByOrderByProductNameDesc(Pageable pageable);
    @Query(value = "select * from product p order by product_id desc ", nativeQuery = true)
    Page<Product> findAllOrderByProduct_idDesc(Pageable pageable);

}
