package com.java.weblaptop.service;

import com.java.weblaptop.dto.BrandDTO;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    public List<BrandDTO> retrieveBrands();

    public Optional<BrandDTO> getBrand(Long brandId) throws ResourceNotFoundException;

    public BrandDTO saveBrand(BrandDTO brandDTO);

    public Boolean deleteBrand(Long brandId) throws ResourceNotFoundException;

    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) throws ResourceNotFoundException;
}
