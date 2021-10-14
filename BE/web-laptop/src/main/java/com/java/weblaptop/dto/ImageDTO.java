package com.java.weblaptop.dto;

import com.java.weblaptop.entity.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    private String imageLink;

    private long product_id;

    public ImageDTO convertToDto(ProductImage image) {
        ImageDTO dto = new ImageDTO();
        dto.setImageLink(image.getImageLink());
        dto.setProduct_id(image.getProduct().getProduct_id());

        return dto;
    }

    public ProductImage convertToEti(ImageDTO dto) {
        ProductImage image = new ProductImage();

        image.setImageLink(dto.getImageLink());

        return image;
    }


    public List<ImageDTO> toListDto(List<ProductImage> listEntity) {
        List<ImageDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

    public List<ProductImage> toListEntity(List<ImageDTO> listDto){
        List<ProductImage> listEntity  = new ArrayList<>();
        listDto.forEach(d->{
            listEntity.add(this.convertToEti(d));
        });
        return listEntity;
    }

}
