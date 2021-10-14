package com.java.weblaptop.service.impl;

import com.java.weblaptop.dto.BrandDTO;
import com.java.weblaptop.dto.ErrorCode;
import com.java.weblaptop.entity.Brand;

import com.java.weblaptop.exception.ResourceNotFoundException;
import com.java.weblaptop.repository.BrandRepository;
import com.java.weblaptop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<BrandDTO> retrieveBrands() {
        List<Brand> brands = brandRepository.findAll();
        return new BrandDTO().toListDto(brands);
    }

    @Override
    public Optional<BrandDTO> getBrand(Long brandId) throws ResourceNotFoundException {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));
        return Optional.of(new BrandDTO().convertToDto(brand));
    }

    @Override
    public BrandDTO saveBrand(BrandDTO brandDTO) {
        Brand brand = new BrandDTO().convertToEti(brandDTO);
        return new BrandDTO().convertToDto(brandRepository.save(brand));
    }

    @Override
    public Boolean deleteBrand(Long brandId) throws ResourceNotFoundException {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));
        this.brandRepository.delete(brand);
        return true;
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) throws ResourceNotFoundException {
        Brand brandExist = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));

        brandExist.setBrandName(brandDTO.getBrandName());

        Brand brand = new Brand();
        brand = brandRepository.save(brandExist);
        return new BrandDTO().convertToDto(brand);
    }
}
