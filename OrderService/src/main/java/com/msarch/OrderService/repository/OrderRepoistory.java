package com.msarch.OrderService.repository;

import com.msarch.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepoistory extends JpaRepository<Order,Long> {
}
