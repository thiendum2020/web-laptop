package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    private long order_id;
    private int totalQty;
    private float totalPrice;
    private String username;
    private String status;


    public OrderResponseDTO convertToDto(Order order) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setOrder_id(order.getOrder_id());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setTotalQty(order.getTotalQty());
        orderDTO.setStatus(order.getStatus().getStatusName());
        orderDTO.setUsername(order.getUser().getUserName());

        return orderDTO;
    }

    public Order convertToEti(OrderResponseDTO orderDTO) {
        Order order = new Order();

        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalQty(orderDTO.getTotalQty());

        return order;
    }


    public List<OrderResponseDTO> toListDto(List<Order> listEntity) {
        List<OrderResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
