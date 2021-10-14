package com.java.weblaptop.controller;


import com.java.weblaptop.dto.*;

import com.java.weblaptop.entity.Product;
import com.java.weblaptop.exception.*;

import com.java.weblaptop.service.ProductService;

import com.java.weblaptop.service.impl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllProduct() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> listResponse = new ArrayList<>();

        try {
            List<ProductResponseDTO> productDTOS = productService.retrieveProducts();
            List list = Collections.synchronizedList(new ArrayList(productDTOS));

            if (listResponse.addAll(list) == true) {
                response.setData(productDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_PRODUCT_SUCCESS);
        } catch (Exception e) {
            throw new GetDataFail("" + ErrorCode.GET_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchcate")
    public ResponseEntity<ResponseDTO> findProductByCate(@RequestParam("category_id") @NotBlank Long categoryId,
                                                            PageDTO pageDTO
    ) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductDTO> productDTOS = productService.findProductByCate(categoryId, pageDTO);
        responseDTO.setData(productDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/sort")
    public ResponseEntity<ResponseDTO> getProductSort(PageDTO pageDTO){
        ResponseDTO responseDTO = new ResponseDTO();
       List<ProductDTO> productDTO = productService.sortProduct(pageDTO);
       responseDTO.setData(productDTO);
       responseDTO.setSuccessCode(SuccessCode.GET_ALL_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<ResponseDTO> findProductByCategory(@PathVariable("category_id") @NotBlank Long cateId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductDTO> productDTOS = productService.findProductByCategory(cateId);
        responseDTO.setData(productDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/brand/{brand_id}")
    public ResponseEntity<ResponseDTO> findProductByBrand(@PathVariable("brand_id") @NotBlank Long brandId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductDTO> productDTOS = productService.findProductByBrand(brandId);
        responseDTO.setData(productDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ResponseDTO> getPro(@PathVariable("product_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<ProductResponseDTO> productDTO = productService.getProduct(id);

            responseDTO.setData(productDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //insert
    @PostMapping("/product")
    public ResponseEntity<ResponseDTO> createPro(@Valid @RequestBody ProductDTO dtoRequest) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            //dtoRequest
            System.out.println("aaaaaaaaaaaaaaaaaaaaa"+dtoRequest);
            ProductDTO dto = productService.saveProduct(dtoRequest);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(e.getMessage()+""+ErrorCode.ADD_PRODUCT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //update
    @PutMapping("/product/{product_id}")
    public ResponseEntity<ResponseDTO> updatePro(@PathVariable(value = "product_id") Long id, @Valid @RequestBody ProductDTO dtoRequest) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
        ProductDTO updatePro = productService.updateProduct(id, dtoRequest);
            responseDTO.setData(updatePro);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ e.getMessage());
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //delete
    @DeleteMapping("/product/{product_id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable(value = "product_id") Long productId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = productService.deleteProduct(productId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_PRODUCT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/admin/{product_id}")
    public ResponseEntity<ResponseDTO> getProductInAdmin(@PathVariable("product_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<ProductDTO> productDTO = productService.getProductInAdmin(id);

            responseDTO.setData(productDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String url = cloudinaryService.uploadFile(file);
        return url;
    }

    @GetMapping("/searchproduct")
    public ResponseEntity<ResponseDTO> findProductExact(
            @RequestParam("productName") String productName, Pageable pageable) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductDTO> productDTOS = productService.searchProduct(productName, pageable);
        responseDTO.setData(productDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> findProductByName(
            @RequestParam("keyword") String productName, Pageable pageable) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductDTO> productDTOS = productService.searchProductByName(productName, pageable);
        responseDTO.setData(productDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/pagination")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "-1") Long cateId,
            @RequestParam(defaultValue = "-1")Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            List<Product> products = new ArrayList<Product>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pagePros = productService.filterProduct(search, cateId, brandId, paging);

            products = pagePros.getContent();
            List<ProductResponseDTO> productDTOS = new ProductResponseDTO().toListDto(products);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOS);
            response.put("currentPage", pagePros.getNumber());
            response.put("totalItems", pagePros.getTotalElements());
            response.put("totalPages", pagePros.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagination/sort/priceasc")
    public ResponseEntity<Map<String, Object>> getProductsSortByPriceAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            List<Product> products = new ArrayList<Product>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pagePros = productService.sortProductByPriceAsc(paging);

            products = pagePros.getContent();
            List<ProductResponseDTO> productDTOS = new ProductResponseDTO().toListDto(products);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOS);
            response.put("currentPage", pagePros.getNumber());
            response.put("totalItems", pagePros.getTotalElements());
            response.put("totalPages", pagePros.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagination/sort/pricedesc")
    public ResponseEntity<Map<String, Object>> getProductsSortByPriceDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            List<Product> products = new ArrayList<Product>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pagePros = productService.sortProductByPriceDesc(paging);

            products = pagePros.getContent();
            List<ProductResponseDTO> productDTOS = new ProductResponseDTO().toListDto(products);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOS);
            response.put("currentPage", pagePros.getNumber());
            response.put("totalItems", pagePros.getTotalElements());
            response.put("totalPages", pagePros.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagination/sort/name")
    public ResponseEntity<Map<String, Object>> getProductsSortByProductName(
            @RequestParam String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            List<Product> products = new ArrayList<Product>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pagePros = productService.sortProductByName(sort, paging);

            products = pagePros.getContent();
            List<ProductResponseDTO> productDTOS = new ProductResponseDTO().toListDto(products);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOS);
            response.put("currentPage", pagePros.getNumber());
            response.put("totalItems", pagePros.getTotalElements());
            response.put("totalPages", pagePros.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pagination/sort/ratingdesc")
    public ResponseEntity<Map<String, Object>> getProductsSortByRatingDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        try {
            List<Product> products = new ArrayList<Product>();
            Pageable paging = PageRequest.of(page, size);
            Page<Product> pagePros = productService.sortProductByRatingDesc(paging);

            products = pagePros.getContent();
            List<ProductResponseDTO> productDTOS = new ProductResponseDTO().toListDto(products);

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOS);
            response.put("currentPage", pagePros.getNumber());
            response.put("totalItems", pagePros.getTotalElements());
            response.put("totalPages", pagePros.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
