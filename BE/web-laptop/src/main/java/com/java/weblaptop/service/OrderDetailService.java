package com.java.weblaptop.service;

import com.java.weblaptop.dto.Dashboard;
import com.java.weblaptop.dto.OrderDetailDTO;

import com.java.weblaptop.dto.OrderDetailResponseDTO;
import com.java.weblaptop.exception.AddDataFail;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    public List<OrderDetailDTO> retrieveOrderDetails();

    public Optional<OrderDetailDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException;

    public OrderDetailDTO saveOrderDetail(OrderDetailDTO detailDTO) throws ResourceNotFoundException, AddDataFail;

    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException;

    public OrderDetailDTO updateOrderDetail(Long id, OrderDetailDTO detailDTO) throws ResourceNotFoundException;

    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException;

    public OrderDetailDTO restoreQty(Long productId) throws ResourceNotFoundException;

    public List<Object> getTopProduct();

    public Dashboard getDashboard() throws Exception;
}
