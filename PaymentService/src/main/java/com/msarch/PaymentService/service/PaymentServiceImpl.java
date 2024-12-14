package com.msarch.PaymentService.service;

import com.msarch.PaymentService.entity.TransactionDetails;
import com.msarch.PaymentService.modal.PaymentMode;
import com.msarch.PaymentService.modal.PaymentRequest;
import com.msarch.PaymentService.modal.PaymentResponse;
import com.msarch.PaymentService.repo.TransactionRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@Log4j2
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private TransactionRepo transactionRepo;


    @Override
    public long doPayment(PaymentRequest request) {
        System.out.println("Request :: " + request);
//        TransactionDetails transactionDetails = new TransactionDetails();
//        transactionDetails.setAmount(request.getAmount());
//        transactionDetails.setPaymentMode(request.getPaymentMode().name());
//        transactionDetails.setOrderId(request.getOrderId());
//        transactionDetails.setPaymentStatus("Success");
//        transactionDetails.setReferenceNumber(request.getReferenceNumber());


        TransactionDetails transactionDetails = TransactionDetails.builder()
                .amount(request.getAmount())
                .orderId(request.getOrderId())
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .referenceNumber(request.getReferenceNumber())
                .build();
        transactionRepo.save(transactionDetails);
        log.info("Transaction done with id :: {}", transactionDetails.getId());
//              System.out.println("Transaction done with id :: {}"+transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting Payment Details for order id :: {}",orderId);
        TransactionDetails transactionDetails = transactionRepo.findByOrderId(Long.valueOf(orderId));
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentDate(transactionDetails.getPaymentDate())
                .orderID(transactionDetails.getOrderId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .amount(transactionDetails.getAmount())
                .status(transactionDetails.getPaymentStatus())
                .build();
        return paymentResponse;
    }
}
