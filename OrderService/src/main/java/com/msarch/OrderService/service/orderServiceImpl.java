package com.msarch.OrderService.service;

import com.msarch.OrderService.entity.Order;
import com.msarch.OrderService.external.client.PaymentService;
import com.msarch.OrderService.external.client.ProductService;
import com.msarch.OrderService.external.request.PaymentRequest;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //order entity -> to save the order with status created
        // Product service -> Block product service to reduce quantity
        // Payment service ->  Payments -> SUCCESS -> COMPLETED else -> CANCELLED

        log.info("Placing order {} ",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status created");

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

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getAmount())
                .build();

        String orderStatus = null;

        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment successful Changing order status to ORDER_PLACED");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.info("Error occurred in the PAYMENT-SERVICE changing status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepoistory.save(order);
        return  order.getOrderId();
    }
}
