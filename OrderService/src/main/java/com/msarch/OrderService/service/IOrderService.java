package com.msarch.OrderService.service;

import com.msarch.OrderService.modal.OrderRequest;
import com.msarch.OrderService.modal.OrderResponse;

public interface IOrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
