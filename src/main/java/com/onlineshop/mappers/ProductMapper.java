package com.onlineshop.mappers;

import com.onlineshop.dtos.ProductDTO;
import com.onlineshop.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice().doubleValue());
        dto.setCategoryNames(product.getCategories()
                .stream()
                .map(category -> category.getName())
                .collect(Collectors.toList()));
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        // This implementation assumes the product categories are already managed elsewhere
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        return product;
    }
}
