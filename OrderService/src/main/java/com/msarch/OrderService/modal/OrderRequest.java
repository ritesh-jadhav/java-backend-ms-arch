package com.msarch.OrderService.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long orderId;
    private  Long productId;
    private  Long quantity;
    private Instant orderDate;
    private  String orderStatus;
    private  long amount;
    private PaymentMode paymentMode;
}
