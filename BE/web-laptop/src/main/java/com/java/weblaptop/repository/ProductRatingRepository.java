package com.java.weblaptop.repository;

import com.java.weblaptop.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    Optional<ProductRating> findByRatingNumber(long ratingNumber);
}
