package com.onlineshop.service;

import com.onlineshop.entities.Customer;
import com.onlineshop.entities.Order;
import com.onlineshop.repositories.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.onlineshop.services.OrderService;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Customer customer;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Initialize mock customer and order objects
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDate(LocalDate.now());
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDate(LocalDate.now().minusDays(1));
        order2.setCustomer(customer);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2L, orders.get(1).getId());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(1L);

        assertFalse(result.isPresent());

        verify(orderRepository, times(1)).findById(1L);
    }
}

