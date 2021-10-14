package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Order;
import com.java.weblaptop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUser(User user);
}
