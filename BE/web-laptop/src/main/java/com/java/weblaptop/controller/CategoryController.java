package com.java.weblaptop.controller;

import com.java.weblaptop.dto.*;
import com.java.weblaptop.exception.*;
import com.java.weblaptop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllCategory() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();

        try {
            List<CategoryDTO> categoryDTOS = categoryService.retrieveCategories();
            List list = Collections.synchronizedList(new ArrayList(categoryDTOS));

            //response.setData(responseDTO.addAll(list));
            if (responseDTO.addAll(list) == true) {
                response.setData(categoryDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ErrorCode.GET_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{category_id}")
    public ResponseEntity<ResponseDTO> getCate(@PathVariable("category_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<CategoryDTO> categoryDTO = categoryService.getCate(id);

            responseDTO.setData(categoryDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_CATEGORY_SUCCESS);
        } catch (Exception e) {
            throw new ResourceNotFoundException(""+ErrorCode.FIND_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //insert

    @PostMapping("/category")
    public ResponseEntity<ResponseDTO> createCate(@Valid @RequestBody CategoryDTO categoryDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CategoryDTO dto = categoryService.saveCategory(categoryDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    @PostMapping("/parent")
//    public ResponseEntity<ResponseDTO> createParent(@Valid @RequestBody CategoryDTO categoryDTO) throws AddDataFail {
//        ResponseDTO responseDTO = new ResponseDTO();
//        try {
//            CategoryDTO dto = categoryService.createParent(categoryDTO);
//            responseDTO.setData(dto);
//            responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
//        } catch (Exception e){
//            throw new AddDataFail(""+ErrorCode.ADD_CATEGORY_ERROR);
//        }
//
//        return ResponseEntity.ok(responseDTO);
//    }

    //update
    @PutMapping("/category/{category_id}")
    public ResponseEntity<ResponseDTO> updateCate(@PathVariable(value = "category_id") Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CategoryDTO updateCate = categoryService.updateCategory(categoryId, categoryDTO);

            responseDTO.setData(updateCate);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //delete
    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<ResponseDTO> deleteCate(@PathVariable(value = "category_id") Long categoryId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = categoryService.deleteCategory(categoryId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    @GetMapping("/parent/{category_id}")
//    public ResponseEntity<ResponseDTO> findCategoryByParent(@PathVariable("category_id") @NotBlank Long cateId) throws ResourceNotFoundException {
//        ResponseDTO responseDTO = new ResponseDTO();
//        List<CategoryDTO> categoryDTOS = categoryService.getCategoryByParent(cateId);
//        responseDTO.setData(categoryDTOS);
//        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
//        return ResponseEntity.ok(responseDTO);
//    }

//    @GetMapping("/parent")
//    public ResponseEntity<ResponseDTO> getParent() {
//        ResponseDTO responseDTO = new ResponseDTO();
//        List<CategoryDTO> categoryDTOS = categoryService.getParent();
//        responseDTO.setData(categoryDTOS);
//        responseDTO.setSuccessCode(SuccessCode.FIND_CATEGORY_SUCCESS);
//        return ResponseEntity.ok(responseDTO);
//    }
}
