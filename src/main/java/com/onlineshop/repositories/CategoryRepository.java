package com.onlineshop.repositories;

import com.onlineshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Example: Find a category by name
    Category findByName(String name);

}
