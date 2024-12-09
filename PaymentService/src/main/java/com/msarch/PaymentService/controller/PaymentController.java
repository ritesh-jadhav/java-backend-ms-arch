package com.msarch.PaymentService.controller;

import com.msarch.PaymentService.modal.PaymentRequest;
import com.msarch.PaymentService.service.IPaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Log4j2
public class PaymentController {

    @Autowired
    private IPaymentService iPaymentService;

     @PostMapping
     public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request){
         return new ResponseEntity<>(iPaymentService.doPayment(request), HttpStatus.OK);
    }


}
