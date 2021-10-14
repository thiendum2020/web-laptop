package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private long brand_id;

    @NotBlank
    private String brandName;

    public BrandDTO convertToDto(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setBrand_id(brand.getBrand_id());
        brandDTO.setBrandName(brand.getBrandName());

        return brandDTO;
    }

    public Brand convertToEti(BrandDTO brandDTO) {
        Brand brand = new Brand();

        brand.setBrand_id(brandDTO.getBrand_id());
        brand.setBrandName(brandDTO.getBrandName());

        return brand;
    }


    public List<BrandDTO> toListDto(List<Brand> listEntity) {
        List<BrandDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
