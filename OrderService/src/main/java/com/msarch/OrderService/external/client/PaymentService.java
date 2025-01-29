package com.msarch.OrderService.external.client;

import com.msarch.OrderService.exception.CustomException;
import com.msarch.OrderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);

    default void  fallback(Exception exception){
        throw new CustomException("PAYMENT_SERVICE_NOT_AVAILABLE","UNAVAILABLE",500);
    }
}

