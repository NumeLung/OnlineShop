package com.onlineshop.controller;

import com.onlineshop.entities.Supplier;
import com.onlineshop.services.SupplierService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.onlineshop.controllers.SupplierController;

@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    @SuppressWarnings("unused")
    private SupplierController supplierController;

    private Supplier supplier;


    @Test
    void testGetAllSuppliers() throws Exception {
        when(supplierService.getAllSuppliers()).thenReturn(List.of(supplier));

        mockMvc.perform(get("/api/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Supplier"));
        
        verify(supplierService, times(1)).getAllSuppliers();
    }

    @Test
    void testGetSupplierById() throws Exception {
        when(supplierService.getSupplierById(1L)).thenReturn(Optional.of(supplier));

        mockMvc.perform(get("/api/suppliers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Supplier"));
        
        verify(supplierService, times(1)).getSupplierById(1L);
    }

    @Test
    void testGetSupplierByIdNotFound() throws Exception {
        when(supplierService.getSupplierById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/suppliers/{id}", 1L))
                .andExpect(status().isNotFound());
        
        verify(supplierService, times(1)).getSupplierById(1L);
    }

    @Test
    void testCreateSupplier() throws Exception {
        when(supplierService.saveSupplier(any(Supplier.class))).thenReturn(supplier);

        mockMvc.perform(post("/api/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Supplier\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Supplier"));

        verify(supplierService, times(1)).saveSupplier(any(Supplier.class));
    }

    @Test
    void testDeleteSupplier() throws Exception {
        doNothing().when(supplierService).deleteSupplier(1L);

        mockMvc.perform(delete("/api/suppliers/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(supplierService, times(1)).deleteSupplier(1L);
    }
}
