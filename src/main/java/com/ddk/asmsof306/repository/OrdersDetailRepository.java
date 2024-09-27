package com.ddk.asmsof306.repository;

import com.ddk.asmsof306.model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
}
