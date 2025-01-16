package com.onlineshop.controller;

import com.onlineshop.entities.Customer;
import com.onlineshop.services.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.onlineshop.controllers.CustomerController;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");
        customer2.setEmail("jane.doe@example.com");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        // When & Then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testGetCustomerById_Found() throws Exception {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    public void testGetCustomerById_NotFound() throws Exception {
        // Given
        when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).saveCustomer(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // Given
        doNothing().when(customerService).deleteCustomer(1L);

        // When & Then
        mockMvc.perform(delete("/api/customers/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(1L);
    }
}
