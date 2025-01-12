package com.onlineshop.repositories;

import com.onlineshop.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Example: Find a supplier by name
    Supplier findByName(String name);
}
