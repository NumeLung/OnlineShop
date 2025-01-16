package com.onlineshop.service;

import com.onlineshop.entities.Order;
import com.onlineshop.entities.OrderDetail;
import com.onlineshop.entities.Product;
import com.onlineshop.repositories.OrderDetailRepository;
import com.onlineshop.repositories.OrderRepository;
import com.onlineshop.repositories.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.onlineshop.services.OrderDetailService;

@SpringBootTest
public class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    @SuppressWarnings("unused")
    private OrderRepository orderRepository;

    @Mock
    @SuppressWarnings("unused")
    private ProductRepository productRepository;

    @InjectMocks
    private OrderDetailService orderDetailService;

    private OrderDetail orderDetail;
    private Order order;
    private Product product;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Initialize test data
        order = new Order();
        product = new Product();

        orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setQuantity(3);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
    }

    @Test
    void testGetAllOrderDetails() {
        // Mock the repository method to return a list of order details
        when(orderDetailRepository.findAll()).thenReturn(Arrays.asList(orderDetail));

        // Call the service method
        var orderDetails = orderDetailService.getAllOrderDetails();

        // Verify the result
        assertNotNull(orderDetails);
        assertEquals(1, orderDetails.size());
        assertEquals(orderDetail.getId(), orderDetails.get(0).getId());
        verify(orderDetailRepository).findAll();  // Ensure the repository method was called
    }

    @Test
    void testGetOrderDetailById_Found() {
        // Mock the repository to return an Optional containing the orderDetail
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));

        // Call the service method
        Optional<OrderDetail> foundOrderDetail = orderDetailService.getOrderDetailById(1L);

        // Verify the result
        assertTrue(foundOrderDetail.isPresent());
        assertEquals(orderDetail.getId(), foundOrderDetail.get().getId());
        verify(orderDetailRepository).findById(1L);  // Ensure the repository method was called
    }

    @Test
    void testGetOrderDetailById_NotFound() {
        // Mock the repository to return an empty Optional (not found)
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method
        Optional<OrderDetail> foundOrderDetail = orderDetailService.getOrderDetailById(1L);

        // Verify the result
        assertFalse(foundOrderDetail.isPresent());  // Should be empty
        verify(orderDetailRepository).findById(1L);  // Ensure the repository method was called
    }

    @Test
    void testSaveOrderDetail() {
        // Mock the repository to save the orderDetail and return it
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        // Call the service method
        OrderDetail savedOrderDetail = orderDetailService.saveOrderDetail(orderDetail);

        // Verify the result
        assertNotNull(savedOrderDetail);
        assertEquals(orderDetail.getId(), savedOrderDetail.getId());
        assertEquals(orderDetail.getQuantity(), savedOrderDetail.getQuantity());
        verify(orderDetailRepository).save(any(OrderDetail.class));  // Ensure the repository method was called
    }

    @Test
    void testDeleteOrderDetail() {
        // Mock the repository method for deleting by ID
        doNothing().when(orderDetailRepository).deleteById(1L);

        // Call the service method
        orderDetailService.deleteOrderDetail(1L);

        // Verify that the repository method was called
        verify(orderDetailRepository).deleteById(1L);
    }

    @Test
    void testGetOrderDetailsByOrderId() {
        // Mock the repository method to return a list of order details by order ID
        when(orderDetailRepository.findByOrderId(anyLong())).thenReturn(Arrays.asList(orderDetail));

        // Call the service method
        var orderDetails = orderDetailService.getOrderDetailsByOrderId(1L);

        // Verify the result
        assertNotNull(orderDetails);
        assertEquals(1, orderDetails.size());
        assertEquals(orderDetail.getId(), orderDetails.get(0).getId());
        verify(orderDetailRepository).findByOrderId(1L);  // Ensure the repository method was called
    }
}
