package com.msarch.OrderService.service;

import com.msarch.OrderService.entity.Order;
import com.msarch.OrderService.modal.OrderRequest;
import com.msarch.OrderService.repository.OrderRepoistory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class orderServiceImpl implements  IOrderService{

    @Autowired
    private OrderRepoistory orderRepoistory;


    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //order entity -> to save the order with status created
        log.info("creating order");
        Order order = Order.builder()
                .orderDate(orderRequest.getOrderDate())
                .orderStatus(orderRequest.getOrderStatus())
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .build();
            orderRepoistory.save(order);
        log.info("order created with id : {}",order.getOrderId());
            // Product service -> Block product service to reduce quantity



            // Payment service ->  Payments -> SUCCESS -> COMPLETED else -> CANCELLED

        return  order.getOrderId();
    }
}
