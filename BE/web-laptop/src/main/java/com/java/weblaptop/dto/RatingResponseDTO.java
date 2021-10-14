package com.java.weblaptop.dto;

import com.java.weblaptop.entity.ProductRating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RatingResponseDTO {
    public long ratingNumber;
    private String comment;
    private String username;
    private String productname;


    public RatingResponseDTO convertToDto(ProductRating rating) {
        RatingResponseDTO ratingDTO = new RatingResponseDTO();
        ratingDTO.setRatingNumber(rating.getRatingNumber());
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setUsername(rating.getUser().getUserName());
        ratingDTO.setProductname(rating.getProduct().getProductName());

        return ratingDTO;
    }

    public ProductRating convertToEti(RatingResponseDTO ratingDTO) {
        ProductRating rating = new ProductRating();

        rating.setRatingNumber(ratingDTO.getRatingNumber());
        rating.setComment(ratingDTO.getComment());

        return rating;
    }


    public List<RatingResponseDTO> toListDto(List<ProductRating> listEntity) {
        List<RatingResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
