package com.msarch.PaymentService.service;

import com.msarch.PaymentService.modal.PaymentRequest;

public interface IPaymentService {
    long doPayment(PaymentRequest request);
}
