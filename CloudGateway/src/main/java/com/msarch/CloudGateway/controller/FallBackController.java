package com.msarch.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/orderServiceFallback")
    public String orderServiceFallback(){
        return  "ORDER SERVICE IS DOWN";
    }

    @GetMapping("/productServiceFallback")
    public String productServiceFallback(){
        return  "PRODUCT SERVICE IS DOWN";
    }
    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback(){
        return  "PAYMENT SERVICE IS DOWN";
    }

}
