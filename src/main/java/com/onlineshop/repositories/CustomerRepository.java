package com.onlineshop.repositories;

import com.onlineshop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Example: Find a customer by email
    Customer findByEmail(String email);
}
