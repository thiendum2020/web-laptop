package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.RatingDTO;
import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.ProductRatingRepository;
import com.rookie.webwatch.repository.Productrepository;
import com.rookie.webwatch.repository.UserRepository;
import com.rookie.webwatch.service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Productrepository productrepository;

    @Override
    public List<RatingDTO> retrieveRatings() {
        List<ProductRating> ratings = ratingRepository.findAll();
        return new RatingDTO().toListDto(ratings);
    }

    @Override
    public Optional<RatingDTO> getRating(Long ratingId) throws ResourceNotFoundException {
        ProductRating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("rating not found for this id: "+ratingId));
        return Optional.of(new RatingDTO().convertToDto(rating));
    }

    @Override
    public RatingDTO saveRating(RatingDTO ratingDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(ratingDTO.getUser_id()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+ratingDTO.getUser_id()));

        Product product = productrepository.findById(ratingDTO.getProduct_id()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+ratingDTO.getProduct_id()));

        ProductRating rating = new RatingDTO().convertToEti(ratingDTO);
        rating.setProduct(product);
        rating.setUser(user);

        return new RatingDTO().convertToDto(ratingRepository.save(rating));
    }

    @Override
    public Boolean deleteRating(Long ratingId) throws ResourceNotFoundException {
        ProductRating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("rating not found for this id: " + ratingId));
        this.ratingRepository.delete(rating);
        return true;
    }

    @Override
    public RatingDTO updateRating(Long id, RatingDTO ratingDTO) throws ResourceNotFoundException {
        ProductRating ratingExist = ratingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("rating not found for this id: "+id));

        ratingExist.setRatingNumber(ratingDTO.getRatingNumber());

        ProductRating rating = new ProductRating();
        rating = ratingRepository.save(ratingExist);
        return new RatingDTO().convertToDto(rating);
    }
}
