package com.msarch.OrderService.service;

import com.msarch.OrderService.entity.Order;
import com.msarch.OrderService.exception.CustomException;
import com.msarch.OrderService.external.client.PaymentService;
import com.msarch.OrderService.external.client.ProductService;
import com.msarch.OrderService.external.request.PaymentRequest;
import com.msarch.OrderService.modal.OrderRequest;
import com.msarch.OrderService.modal.OrderResponse;
import com.msarch.OrderService.repository.OrderRepoistory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class orderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepoistory orderRepoistory;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //order entity -> to save the order with status created
        // Product service -> Block product service to reduce quantity
        // Payment service ->  Payments -> SUCCESS -> COMPLETED else -> CANCELLED

        log.info("Placing order {} ", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
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
        log.info("order created with id : {}", order.getOrderId());

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment successful Changing order status to ORDER_PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.info("Error occurred in the PAYMENT-SERVICE changing status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepoistory.save(order);
        return order.getOrderId();
    }


    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for order id :: {}", orderId);
        Order order = orderRepoistory.findById(orderId).orElseThrow(() -> new CustomException(
                "Order not found for the order id :: " + orderId,
                "NOT_FOUND",
                404));
        log.info("invoking product service to fetch product details for product id :: {}", order.getProductId());

        OrderResponse.ProductDetails productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                OrderResponse.ProductDetails.class);
        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .build();

        OrderResponse response = OrderResponse.builder()
                .orderId(order.getOrderId())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .build();
        return response;
    }
}
