package com.java.weblaptop.service;

import com.java.weblaptop.dto.RatingDTO;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductRatingService {
    public List<RatingDTO> retrieveRatings();

    public Optional<RatingDTO> getRating(Long ratingId) throws ResourceNotFoundException;

    public RatingDTO saveRating(RatingDTO ratingDTO) throws ResourceNotFoundException;

    public Boolean deleteRating(Long ratingId) throws ResourceNotFoundException;

    public RatingDTO updateRating(Long id, RatingDTO ratingDTO) throws ResourceNotFoundException;
}
