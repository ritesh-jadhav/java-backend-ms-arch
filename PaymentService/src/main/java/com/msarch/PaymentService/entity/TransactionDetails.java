package com.msarch.PaymentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "TRANSACTION_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "mode")
    private String paymentMode;
    @Column(name = "ref_no")
    private String referenceNumber;
    @Column(name = "date")
    private Instant paymentDate;
    @Column(name = "status")
    private String paymentStatus;
    @Column(name = "amount")
    private long amount;

}
