package com.onlineshop.service;

import com.onlineshop.controllers.ProductController;
import com.onlineshop.entities.Product;
import com.onlineshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(BigDecimal.valueOf(99.99));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> productList = List.of(product);

        // Mock the service method to return a list of products
        when(productService.getAllProducts()).thenReturn(productList);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Sample Product"));
        
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        // Mock the service method to return a product
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(99.99));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        // Mock the service method to return an empty Optional
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testCreateProduct() throws Exception {
        // Mock the service method to return the product
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Sample Product\", \"price\": 99.99}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(99.99));

        verify(productService, times(1)).saveProduct(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Perform the DELETE request and verify the response
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        // Verify that the delete method was called once
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testGetProductsByCategoryName() throws Exception {
        List<Product> productList = List.of(product);

        // Mock the service method to return products by category name
        when(productService.getProductsByCategoryName("Electronics")).thenReturn(productList);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/products/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Sample Product"));

        verify(productService, times(1)).getProductsByCategoryName("Electronics");
    }
}
