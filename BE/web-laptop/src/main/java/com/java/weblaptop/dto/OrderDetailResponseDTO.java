package com.java.weblaptop.dto;

import com.java.weblaptop.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailResponseDTO {
    @NotNull
    private long detail_id;

    @NotNull
    private int detailQty;

    @NotNull
    private float detailPrice;
    private long order_id;
    private String name;
    private List<ImageDTO> image;


    public OrderDetailResponseDTO convertToDto(OrderDetail detail) {
        OrderDetailResponseDTO deatailDTO = new OrderDetailResponseDTO();
        deatailDTO.setDetail_id(detail.getDetail_id());
        deatailDTO.setDetailQty(detail.getDetailQty());
        deatailDTO.setDetailPrice(detail.getDetailPrice());
        deatailDTO.setOrder_id(detail.getOrder().getOrder_id());
        deatailDTO.setName(detail.getProduct().getProductName());

        List<ImageDTO> listDto = new ArrayList<>();

        if(detail.getProduct().getProductImages()!=null){
            detail.getProduct().getProductImages().forEach(e -> {
                listDto.add(new ImageDTO().convertToDto(e));
            });
        }
        deatailDTO.setImage(listDto);

        return deatailDTO;
    }

    public OrderDetail convertToEti(OrderDetailResponseDTO deatailDTO) {
        OrderDetail detail = new OrderDetail();

        detail.setDetailQty(deatailDTO.getDetailQty());
        detail.setDetailPrice(deatailDTO.getDetailPrice());

        return detail;
    }


    public List<OrderDetailResponseDTO> toListDto(List<OrderDetail> listEntity) {
        List<OrderDetailResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
