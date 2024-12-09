package com.msarch.OrderService.external.request;


import com.msarch.OrderService.modal.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private  long orderId;
    private  String referenceNumber;
    private long amount;
    private PaymentMode paymentMode;
}
