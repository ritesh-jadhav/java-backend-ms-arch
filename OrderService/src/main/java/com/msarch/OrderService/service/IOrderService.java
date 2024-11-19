package com.msarch.OrderService.service;

import com.msarch.OrderService.modal.OrderRequest;

public interface IOrderService {
    long placeOrder(OrderRequest orderRequest);
}
