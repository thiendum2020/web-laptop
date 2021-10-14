package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private long product_id;
//sao có 2 cái product nè, 1 cái respon là trả dề, còn cái này để làm gì à chắc cái này resquest quá :V
    //đúng r nó để request đấy
    @NotBlank
    private String productName;

    @NotNull
    @Min(value = 1)
    private float productPrice;

    @NotBlank
    private String productDescription;

    @Min(value = 0)
    private long productQty;
    private long category_id;
    private long brand_id;

    private List<ImageDTO> imageDTOS;

    private List<RatingDTO> ratingDTOS;
    //private Set<Long> productRatings;


    public ProductDTO(String productName, float productPrice, String productDescription, long productQty, long category_id) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQty = productQty;
        this.category_id = category_id;
    }

    public ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProduct_id(product.getProduct_id());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setProductQty(product.getProductQty());
        productDTO.setCategory_id(product.getCategory().getCategory_id());
        productDTO.setBrand_id(product.getBrand().getBrand_id());

        List<ImageDTO> listDto = new ArrayList<>();

        if(product.getProductImages()!=null){
            product.getProductImages().forEach(e -> {
                listDto.add(new ImageDTO().convertToDto(e));
            });
        }
        productDTO.setImageDTOS(listDto);


        List<RatingDTO> ratings = new ArrayList<>();
        if(product.getProductRatings()!=null) {
            product.getProductRatings().forEach(rating -> {
                ratings.add(new RatingDTO().convertToDto(rating));
            });
        }
        productDTO.setRatingDTOS(ratings);



        return productDTO;
    }

    public Product convertToEti(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductDescription(productDTO.getProductDescription());
        //product.setProductQty(productDTO.getProductQty());

        //product.setRatingTB(0);
        return product;
    }


    public List<ProductDTO> toListDto(List<Product> listEntity) {
        List<ProductDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }


}
