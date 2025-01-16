package com.onlineshop.controller;

import com.onlineshop.entities.Customer;
import com.onlineshop.entities.Order;
import com.onlineshop.services.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.onlineshop.controllers.OrderController;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Initialize MockMvc for testing the controller
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() throws Exception {
        Customer customer = new Customer(); // Assuming Customer is a class
        customer.setId(1L);
        customer.setName("John Doe");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDate(LocalDate.now());
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setCustomer(customer);

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].customer.id").value(1))
                .andExpect(jsonPath("$[1].customer.id").value(1));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception {
        Customer customer = new Customer(); // Mock a customer
        customer.setId(1L);
        customer.setName("John Doe");

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customer.id").value(1))
                .andExpect(jsonPath("$.orderDate").value(LocalDate.now().toString()));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testGetOrdersPlacedAfter() throws Exception {
        Customer customer = new Customer(); // Mock a customer
        customer.setId(1L);
        customer.setName("John Doe");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDate(LocalDate.now());
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setCustomer(customer);

        when(orderService.getOrdersPlacedAfter(LocalDate.parse("2025-01-01"))).thenReturn(Arrays.asList(order1));

        mockMvc.perform(get("/api/orders/after/2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(orderService, times(1)).getOrdersPlacedAfter(LocalDate.parse("2025-01-01"));
    }

    @Test
    void testCreateOrder() throws Exception {
        Customer customer = new Customer(); // Mock a customer
        customer.setId(1L);
        customer.setName("John Doe");

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setOrderDate(LocalDate.now());
        savedOrder.setCustomer(customer);

        when(orderService.saveOrder(order)).thenReturn(savedOrder);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderDate\": \"2025-01-17\", \"customer\": {\"id\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderDate").value("2025-01-17"))
                .andExpect(jsonPath("$.customer.id").value(1));

        verify(orderService, times(1)).saveOrder(order);
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }
}
