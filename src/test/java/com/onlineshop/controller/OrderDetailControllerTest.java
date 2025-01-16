package com.onlineshop.controller;

import com.onlineshop.entities.OrderDetail;
import com.onlineshop.services.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.onlineshop.controllers.OrderDetailController;

@WebMvcTest(OrderDetailController.class)
public class OrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderDetailService orderDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDetail orderDetail;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Initialize test data
        orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setQuantity(3);
        // Assuming Order and Product entities are set up here as well
    }

    @Test
    void testGetAllOrderDetails() throws Exception {
        // Mock the service method to return a list of order details
        when(orderDetailService.getAllOrderDetails()).thenReturn(Arrays.asList(orderDetail));

        // Perform a GET request to /api/order-details
        mockMvc.perform(get("/api/order-details"))
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(jsonPath("$[0].id").value(orderDetail.getId()))
                .andExpect(jsonPath("$[0].quantity").value(orderDetail.getQuantity()));
        
        // Verify that the service method was called once
        verify(orderDetailService).getAllOrderDetails();
    }

    @Test
    void testGetOrderDetailById() throws Exception {
        // Mock the service method to return an order detail by ID
        when(orderDetailService.getOrderDetailById(1L)).thenReturn(Optional.of(orderDetail));

        // Perform a GET request to /api/order-details/{id}
        mockMvc.perform(get("/api/order-details/1"))
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(jsonPath("$.id").value(orderDetail.getId()))
                .andExpect(jsonPath("$.quantity").value(orderDetail.getQuantity()));
        
        // Verify that the service method was called once
        verify(orderDetailService).getOrderDetailById(1L);
    }

    @Test
    void testGetOrderDetailById_NotFound() throws Exception {
        // Mock the service method to return an empty Optional (not found)
        when(orderDetailService.getOrderDetailById(1L)).thenReturn(Optional.empty());

        // Perform a GET request to /api/order-details/{id}
        mockMvc.perform(get("/api/order-details/1"))
                .andExpect(status().isNotFound());  // Expect a 404 Not Found status
        
        // Verify that the service method was called once
        verify(orderDetailService).getOrderDetailById(1L);
    }

    @Test
    void testCreateOrderDetail() throws Exception {
        // Mock the service method to save and return the order detail
        when(orderDetailService.saveOrderDetail(any(OrderDetail.class))).thenReturn(orderDetail);

        // Perform a POST request to /api/order-details
        mockMvc.perform(post("/api/order-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDetail)))  // Convert orderDetail to JSON
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(jsonPath("$.id").value(orderDetail.getId()))
                .andExpect(jsonPath("$.quantity").value(orderDetail.getQuantity()));
        
        // Verify that the service method was called once
        verify(orderDetailService).saveOrderDetail(any(OrderDetail.class));
    }

    @Test
    void testDeleteOrderDetail() throws Exception {
        // Perform a DELETE request to /api/order-details/{id}
        mockMvc.perform(delete("/api/order-details/1"))
                .andExpect(status().isNoContent());  // Expect a 204 No Content status
        
        // Verify that the service method was called once
        verify(orderDetailService).deleteOrderDetail(1L);
    }
}
