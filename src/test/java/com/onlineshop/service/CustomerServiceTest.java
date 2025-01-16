package com.onlineshop.service;

import java.util.List;

import com.onlineshop.entities.Customer;
import com.onlineshop.repositories.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.onlineshop.services.CustomerService;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    public void testGetAllCustomers() {
        // Given
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<Customer> customers = customerService.getAllCustomers();

        // Then
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById_Found() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // When
        Optional<Customer> foundCustomer = customerService.getCustomerById(1L);

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().getName());

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerById_NotFound() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Customer> foundCustomer = customerService.getCustomerById(1L);

        // Then
        assertFalse(foundCustomer.isPresent());

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveCustomer() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Then
        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getName());
        assertEquals("john.doe@example.com", savedCustomer.getEmail());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {
        // Given
        doNothing().when(customerRepository).deleteById(1L);

        // When
        customerService.deleteCustomer(1L);

        // Then
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetCustomerByEmail() {
        // Given
        when(customerRepository.findByEmail("john.doe@example.com")).thenReturn(customer);

        // When
        Customer foundCustomer = customerService.getCustomerByEmail("john.doe@example.com");

        // Then
        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("john.doe@example.com", foundCustomer.getEmail());

        verify(customerRepository, times(1)).findByEmail("john.doe@example.com");
    }
}
