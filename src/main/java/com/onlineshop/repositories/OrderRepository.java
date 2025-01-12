package com.onlineshop.repositories;

import com.onlineshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Example: Find orders by customer ID
    List<Order> findByCustomerId(Long customerId);

    // Example: Find orders placed after a specific date using @Query
    @Query("SELECT o FROM Order o WHERE o.orderDate > :orderDate")
    List<Order> findOrdersPlacedAfter(LocalDate orderDate);
}
