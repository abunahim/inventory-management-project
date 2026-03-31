package com.inventory.inventory_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.inventory_management.config.JwtService;
import com.inventory.inventory_management.dto.ProductRequestDTO;
import com.inventory.inventory_management.dto.ProductResponseDTO;
import com.inventory.inventory_management.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    private ProductRequestDTO buildRequest(String name, Double price, Integer quantity) {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setQuantity(quantity);
        return dto;
    }

    private ProductResponseDTO buildResponse(Long id, String name, Double price, Integer quantity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setPrice(price);
        dto.setQuantity(quantity);
        return dto;
    }

    @Test
    @WithMockUser
    void createProduct_ShouldReturn201() throws Exception {
        ProductResponseDTO response = buildResponse(1L, "Laptop", 999.99, 10);
        when(productService.saveProduct(any())).thenReturn(response);

        mockMvc.perform(post("/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest("Laptop", 999.99, 10))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99));
    }

    @Test
    @WithMockUser
    void createProduct_ShouldReturn400_WhenInvalidData() throws Exception {
        mockMvc.perform(post("/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest("", -5.0, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.quantity").exists());
    }

    @Test
    @WithMockUser
    void getAllProducts_ShouldReturn200() throws Exception {
        when(productService.getAllProducts())
                .thenReturn(List.of(buildResponse(1L, "Mouse", 29.99, 50)));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void getProductById_ShouldReturn200_WhenExists() throws Exception {
        when(productService.getProductById(1L))
                .thenReturn(buildResponse(1L, "Monitor", 299.99, 20));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    @WithMockUser
    void getProductById_ShouldReturn404_WhenNotExists() throws Exception {
        when(productService.getProductById(999L))
                .thenThrow(new RuntimeException("Product not found with id: 999"));

        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not found with id: 999"));
    }

    @Test
    @WithMockUser
    void updateProduct_ShouldReturn200() throws Exception {
        ProductResponseDTO updated = buildResponse(1L, "Gaming Laptop", 1299.99, 5);
        when(productService.updateProduct(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest("Gaming Laptop", 1299.99, 5))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaming Laptop"))
                .andExpect(jsonPath("$.price").value(1299.99));
    }

    @Test
    @WithMockUser
    void deleteProduct_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/products/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}