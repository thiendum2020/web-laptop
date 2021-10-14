package com.java.weblaptop.service.impl;

import com.java.weblaptop.dto.ErrorCode;
import com.java.weblaptop.dto.OrderDTO;
import com.java.weblaptop.dto.OrderResponseDTO;
import com.java.weblaptop.entity.*;
import com.java.weblaptop.exception.ResourceNotFoundException;
import com.java.weblaptop.repository.OrderRepository;
import com.java.weblaptop.repository.Productrepository;
import com.java.weblaptop.repository.StatusRepository;
import com.java.weblaptop.repository.UserRepository;
import com.java.weblaptop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private Productrepository productrepository;

    @Override
    public List<OrderResponseDTO> retrieveOrders() {
        List<Order> orders = orderRepository.findAll();

        return new OrderResponseDTO().toListDto(orders);
    }

    @Override
    public Optional<OrderDTO> getOrder(Long orderId) throws ResourceNotFoundException {
        Order orders = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: "+orderId));
        return Optional.of(new OrderDTO().convertToDto(orders));
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) throws ResourceNotFoundException {

        User user = userRepository.findById(orderDTO.getUser_id()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+orderDTO.getUser_id()));

        Status status = statusRepository.findById(orderDTO.getStatus_id()).orElseThrow(() ->
                new ResourceNotFoundException("status not found for this id: "+orderDTO.getStatus_id()));

        Order order = new OrderDTO().convertToEti(orderDTO);
        order.setStatus(status);
        order.setUser(user);
        return new OrderDTO().convertToDto(orderRepository.save(order));
    }

    @Override
    public Boolean deleteOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: " + orderId));
        this.orderRepository.delete(order);
        return true;
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));

        Status status = statusRepository.findById(orderDTO.getStatus_id()).orElseThrow(() ->
                new ResourceNotFoundException("status not found for this id: "+orderDTO.getStatus_id()));

        orderExist.setTotalQty(orderDTO.getTotalQty());
        orderExist.setTotalPrice(orderDTO.getTotalPrice());
        orderExist.setStatus(status);

        Order order = new Order();
        order = orderRepository.save(orderExist);
        return new OrderDTO().convertToDto(order);

    }

    @Override
    public List<OrderResponseDTO> findOrderByUser(Long userId) throws ResourceNotFoundException {
        Optional<User> userExist = userRepository.findById(userId);
        if(!userExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_USER_ERROR);
        }
        User user = userExist.get();

        List<Order> list = null;
        list = orderRepository.getOrderByUser(user);

        List<OrderResponseDTO> orderDTOS = new ArrayList<>();
        orderDTOS = new OrderResponseDTO().toListDto(list);
        return orderDTOS;
    }

    @Override
    public OrderDTO updateStatusOrder(Long orderId, String status) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));

        if(status.equals("waiting confirm")){
            Status stt = statusRepository.findAllByStatusName(status);
            orderExist.setStatus(stt);
        } else if(status.equals("confirmed")){
            Status stt = statusRepository.findAllByStatusName(status);
            orderExist.setStatus(stt);

        } else if(status.equals("cancel")) {
            Status stt = statusRepository.findAllByStatusName(status);
            orderExist.setStatus(stt);
            orderExist.getOrderDetails().forEach(i -> {
                Product product = i.getProduct();
                product.setProductQty(product.getProductQty() + i.getDetailQty());
                productrepository.save(product);
            });
        } else if(status.equals("complete")) {
            Status stt = statusRepository.findAllByStatusName(status);
            orderExist.setStatus(stt);
        }

        //Product product = new Product();
        Order order = new Order();
        order = orderRepository.save(orderExist);
//        if(order.getStatus().equals("confirmed")){
//            order.getOrderDetails().forEach(detail -> {
//                if(detail.getProduct().getProduct_id() == product.getProduct_id()){
//                    product.setProductQty(product.getProductQty() - detail.getDetailQty());
//                    productrepository.save(product);
//                }
//            });
//        }
        return new OrderDTO().convertToDto(order);
    }
}
