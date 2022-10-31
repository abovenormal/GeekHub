package com.GeekHub.userservice.db.repository;

import com.GeekHub.userservice.db.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    Optional<Orders> findByOrderIdx(String orderIdx);
}
