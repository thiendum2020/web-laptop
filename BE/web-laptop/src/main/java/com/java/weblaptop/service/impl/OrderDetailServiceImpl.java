package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.*;

import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.AddDataFail;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.OrderDetailRepository;
import com.rookie.webwatch.repository.OrderRepository;
import com.rookie.webwatch.repository.Productrepository;
import com.rookie.webwatch.repository.UserRepository;
import com.rookie.webwatch.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Productrepository productrepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderDetailDTO> retrieveOrderDetails() {
        List<OrderDetail> details = detailRepository.findAll();
        int count = 0;
        //count = details.size();
        List<Product> products = productrepository.findAll();

        for (OrderDetail detail : details) {
            for (Product product : products) {
                if (detail.getProduct().getProduct_id() == product.getProduct_id()) {
                    count += 1;

                }
            }

        }
        System.out.print("=========" + count);
        return new OrderDetailDTO().toListDto(details);
    }

    @Override
    public Optional<OrderDetailDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException {
        OrderDetail detail = detailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("detail not found for this id: "+detailId));
        return Optional.of(new OrderDetailDTO().convertToDto(detail));
    }

    @Override
    public OrderDetailDTO saveOrderDetail(OrderDetailDTO detailDTO) throws ResourceNotFoundException, AddDataFail {
        Order order = orderRepository.findById(detailDTO.getOrder_id()).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+detailDTO.getOrder_id()));

        Product product = productrepository.findById(detailDTO.getProduct_id()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+detailDTO.getProduct_id()));

        OrderDetail detail = new OrderDetailDTO().convertToEti(detailDTO);
        if(detailDTO.getDetailQty() > product.getProductQty()){
            throw new AddDataFail(""+ErrorCode.ADD_ORDER_DETAIL_ERROR);
        } else {
            detail.setOrder(order);
            detail.setProduct(product);

            product.setProductQty(product.getProductQty() - detailDTO.getDetailQty());

            productrepository.save(product);
        }

        return new OrderDetailDTO().convertToDto(detailRepository.save(detail));
    }

    @Override
    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException {
        OrderDetail detail = detailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("order detail not found for this id: " + detailId));
        this.detailRepository.delete(detail);
        return true;
    }

    @Override
    public OrderDetailDTO updateOrderDetail(Long id, OrderDetailDTO detailDTO) throws ResourceNotFoundException {
        OrderDetail detailExist = detailRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("order detail not found for this id: "+id));

        detailExist.setDetailQty(detailDTO.getDetailQty());
        detailExist.setDetailPrice(detailDTO.getDetailPrice());

        OrderDetail detail = new OrderDetail();
        detail = detailRepository.save(detailExist);
        return new OrderDetailDTO().convertToDto(detail);
    }

    @Override
    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException {
        Optional<Order> orderExist = orderRepository.findById(orderId);
        if(!orderExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_ORDER_ERROR);
        }
        Order order = orderExist.get();

        List<OrderDetail> list = null;
        list = detailRepository.findOrderDetailsByOrder(order);

        List<OrderDetailResponseDTO> detailDTOS = new ArrayList<>();
        detailDTOS = new OrderDetailResponseDTO().toListDto(list);
        return detailDTOS;
    }

    @Override
    public OrderDetailDTO restoreQty(Long productId) throws ResourceNotFoundException {
        Product product = productrepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+productId));

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        product.setProductQty(product.getProductQty() + detail.getDetailQty());
        productrepository.save(product);

        return new OrderDetailDTO().convertToDto(detailRepository.save(detail));
    }

    @Override
    public List<Object> getTopProduct() {
        List<Object> topProducts = new ArrayList<>();
        Query query = null;
        query = entityManager.createNativeQuery("select p.product_id, p.product_name, subR.sum, subR.my_rank from ( select *, rank() over ( order by sub.sum desc) as my_rank \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tfrom ( select product_id, sum(detail_qty) from order_detail od group by product_id )as sub) as subR ,  product p\n" +
                "where p.product_id = subR.product_id and subR.my_rank <= 10 order by subR.my_rank" );
        topProducts = query.getResultList();
        int count = topProducts.size();
//        for (int i=0; i<=count; i++){
//            System.out.print(query.getParameterValue(i)+"------"+query.getParameter(i));
//            //topProducts.get(i).setProductName((String) query.getParameterValue(i));
//        }
        return topProducts;
    }

    @Override
    public Dashboard getDashboard() throws Exception {
        Dashboard dashboard = new Dashboard();
        try {
            List<Product> products = productrepository.findAll();
            List<User> users = userRepository.findAll();
            List<Order> orders = orderRepository.findAll();
            int countProduct = products.size();
            int countUser = users.size();
            int countOrder = orders.size();
            float price = 0;
            for (Order order : orders) {
                price = price + order.getTotalPrice();
            }
            dashboard.setProduct(countProduct);
            dashboard.setUser(countUser);
            dashboard.setOrder(countOrder);
            dashboard.setTotalPrice(price);

        } catch (Exception e){
            throw new Exception();
        }
        return dashboard;
    }
}

