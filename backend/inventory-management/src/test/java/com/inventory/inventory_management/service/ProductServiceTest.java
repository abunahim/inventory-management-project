package com.inventory.inventory_management.service;

import com.inventory.inventory_management.dto.ProductRequestDTO;
import com.inventory.inventory_management.dto.ProductResponseDTO;
import com.inventory.inventory_management.model.Product;
import com.inventory.inventory_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 999.99, 10);
        product.setId(1L);

        requestDTO = new ProductRequestDTO();
        requestDTO.setName("Laptop");
        requestDTO.setPrice(999.99);
        requestDTO.setQuantity(10);
    }

    @Test
    void getAllProducts_ShouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void saveProduct_ShouldReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.saveProduct(requestDTO);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(999.99, result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.getProductById(99L));

        assertEquals("Product not found with id: 99", ex.getMessage());
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        ProductRequestDTO updateDTO = new ProductRequestDTO();
        updateDTO.setName("Gaming Laptop");
        updateDTO.setPrice(1299.99);
        updateDTO.setQuantity(5);

        Product updated = new Product("Gaming Laptop", 1299.99, 5);
        updated.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updated);

        ProductResponseDTO result = productService.updateProduct(1L, updateDTO);

        assertEquals("Gaming Laptop", result.getName());
        assertEquals(1299.99, result.getPrice());
    }

    @Test
    void deleteProduct_ShouldCallDelete_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.deleteProduct(99L));

        assertEquals("Product not found with id: 99", ex.getMessage());
    }
}
