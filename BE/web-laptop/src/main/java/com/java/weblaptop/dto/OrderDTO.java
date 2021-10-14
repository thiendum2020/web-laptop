package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private long order_id;

    @NotNull
    private int totalQty;

    @NotNull
    private float totalPrice;
    private long user_id;
    private long status_id;


    public OrderDTO convertToDto(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrder_id(order.getOrder_id());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setTotalQty(order.getTotalQty());
        orderDTO.setStatus_id(order.getStatus().getStatus_id());
        orderDTO.setUser_id(order.getUser().getUser_id());

        return orderDTO;
    }

    public Order convertToEti(OrderDTO orderDTO) {
        Order order = new Order();

        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalQty(orderDTO.getTotalQty());

        return order;
    }


    public List<OrderDTO> toListDto(List<Order> listEntity) {
        List<OrderDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }


    public OrderDTO(int totalQty, float totalPrice, long user_id, long status_id) {
        this.totalQty = totalQty;
        this.totalPrice = totalPrice;
        this.user_id = user_id;
        this.status_id = status_id;
    }

}
