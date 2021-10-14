package com.java.weblaptop.controller;

import com.java.weblaptop.dto.ErrorCode;
import com.java.weblaptop.dto.RatingDTO;
import com.java.weblaptop.dto.ResponseDTO;
import com.java.weblaptop.dto.SuccessCode;
import com.java.weblaptop.exception.*;
import com.java.weblaptop.service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ratings")
public class ProductRatingController {
    @Autowired
    private ProductRatingService ratingService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllRating() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<RatingDTO> ratings = ratingService.retrieveRatings();
            List list = Collections.synchronizedList(new ArrayList(ratings));

            if (responseDTO.addAll(list) == true) {
                response.setData(ratings);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_PRODUCT_RATING_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_PRODUCT_RATING_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{rating_id}")
    public ResponseEntity<ResponseDTO> findRating(@PathVariable("rating_id") Long ratingId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<RatingDTO> ratingDTO = ratingService.getRating(ratingId);

            responseDTO.setData(ratingDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_RATING_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_RATING_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //save employee
    @PostMapping("/rating")
    public ResponseEntity<ResponseDTO> createRating(@RequestBody RatingDTO ratingDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RatingDTO dto = ratingService.saveRating(ratingDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_RATING_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_PRODUCT_RATING_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
//    //update
    @PutMapping("/rating/{rating_id}")
    public ResponseEntity<ResponseDTO> updateRating(@PathVariable(value = "rating_id") Long ratingId,
                                                 @RequestBody RatingDTO ratingDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RatingDTO updateRating = ratingService.updateRating(ratingId, ratingDTO);

            responseDTO.setData(updateRating);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_RATING_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_PRODUCT_RATING_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
//    //delete
    @DeleteMapping("/rating/{rating_id}")
    public ResponseEntity<ResponseDTO> deleteRating(@PathVariable(value = "rating_id") Long ratingId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = ratingService.deleteRating(ratingId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_RATING_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_PRODUCT_RATING_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
