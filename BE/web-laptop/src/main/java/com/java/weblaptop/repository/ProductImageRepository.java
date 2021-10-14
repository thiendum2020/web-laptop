package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Product;
import com.java.weblaptop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByImageLink(String imageLink);
    void deleteAllByProduct(Product product);
}
