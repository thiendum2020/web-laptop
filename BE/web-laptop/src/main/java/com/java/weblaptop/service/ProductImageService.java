package com.java.weblaptop.service;

import com.java.weblaptop.dto.ImageDTO;

import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    public List<ImageDTO> retrieveProductImages();

    public Optional<ImageDTO> getProductImage(Long imageId) throws ResourceNotFoundException;

    public ImageDTO saveProductImage(ImageDTO imageDTO) throws ResourceNotFoundException;

    public Boolean deleteProductImage(Long imageId) throws ResourceNotFoundException;

    public ImageDTO updateProductImage(Long id, ImageDTO imageDTO) throws ResourceNotFoundException;
}
