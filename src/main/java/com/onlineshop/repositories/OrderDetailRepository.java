package com.onlineshop.repositories;

import com.onlineshop.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Example: Find all order details for a specific order ID
    List<OrderDetail> findByOrderId(Long orderId);
}
