package com.java.weblaptop.dto;

import com.java.weblaptop.entity.ProductRating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {
    @Min(value = 0)
    @Max(value = 5)
    private long ratingNumber;
    private String comment;
    private long user_id;
    private long product_id;


    public RatingDTO convertToDto(ProductRating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRatingNumber(rating.getRatingNumber());
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setUser_id(rating.getUser().getUser_id());
        ratingDTO.setProduct_id(rating.getProduct().getProduct_id());

        return ratingDTO;
    }

    public ProductRating convertToEti(RatingDTO ratingDTO) {
        ProductRating rating = new ProductRating();

        rating.setRatingNumber(ratingDTO.getRatingNumber());
        rating.setComment(ratingDTO.getComment());

        return rating;
    }


    public List<RatingDTO> toListDto(List<ProductRating> listEntity) {
        List<RatingDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

    public List<ProductRating> toListEntity(List<RatingDTO> listDto) {
        List<ProductRating> listEntity = new ArrayList<>();

        listDto.forEach(r->{
            listEntity.add(this.convertToEti(r));
        });
        return listEntity;
    }

}
