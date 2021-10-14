package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Order;
import com.java.weblaptop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findOrderDetailsByOrder(Order order);
}
