package com.msarch.OrderService.controller;


import com.msarch.OrderService.modal.OrderRequest;
import com.msarch.OrderService.modal.OrderResponse;
import com.msarch.OrderService.service.IOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private IOrderService service;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = service.placeOrder(orderRequest);
        log.info("order id {}:", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable("orderId") long orderId) {
        log.info("Getting order details for order id :: {}", orderId);
        OrderResponse orderResponse = service.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
