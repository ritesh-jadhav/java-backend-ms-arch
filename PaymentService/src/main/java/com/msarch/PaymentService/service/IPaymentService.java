package com.msarch.PaymentService.service;

import com.msarch.PaymentService.modal.PaymentRequest;
import com.msarch.PaymentService.modal.PaymentResponse;

public interface IPaymentService {
    long doPayment(PaymentRequest request);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
