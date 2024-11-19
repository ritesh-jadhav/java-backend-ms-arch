package com.msarch.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "order_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long orderId;
        @Column(name = "PRODUCT_NAME")
        private  Long productId;
        @Column(name = "QUANTITY")
        private  Long quantity;
        @Column(name = "ORDER_DATE")
        private Instant orderDate;
    @Column(name =  "STATUS")
        private  String orderStatus;
    @Column(name = "TOTAL")
        private  long amount;
}
