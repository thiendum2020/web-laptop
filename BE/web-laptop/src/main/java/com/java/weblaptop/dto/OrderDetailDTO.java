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
public class OrderDetailDTO {
    @NotNull
    private long detail_id;

    @NotNull
    private int detailQty;

    @NotNull
    private float detailPrice;
    private long order_id;
    private long product_id;


    public OrderDetailDTO convertToDto(OrderDetail detail) {
        OrderDetailDTO deatailDTO = new OrderDetailDTO();
        deatailDTO.setDetail_id(detail.getDetail_id());
        deatailDTO.setDetailQty(detail.getDetailQty());
        deatailDTO.setDetailPrice(detail.getDetailPrice());
        deatailDTO.setOrder_id(detail.getOrder().getOrder_id());
        deatailDTO.setProduct_id(detail.getProduct().getProduct_id());

        return deatailDTO;
    }

    public OrderDetail convertToEti(OrderDetailDTO deatailDTO) {
        OrderDetail detail = new OrderDetail();

        detail.setDetailQty(deatailDTO.getDetailQty());
        detail.setDetailPrice(deatailDTO.getDetailPrice());

        return detail;
    }


    public List<OrderDetailDTO> toListDto(List<OrderDetail> listEntity) {
        List<OrderDetailDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

}
