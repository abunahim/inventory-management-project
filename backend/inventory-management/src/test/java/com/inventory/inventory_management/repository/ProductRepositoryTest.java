package com.inventory.inventory_management.repository;

import com.inventory.inventory_management.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {
        Product product = new Product("Keyboard", 49.99, 100);
        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Keyboard", found.get().getName());
    }

    @Test
    void shouldReturnAllProducts() {
        productRepository.save(new Product("Mouse", 29.99, 50));
        productRepository.save(new Product("Monitor", 299.99, 20));

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void shouldDeleteProduct() {
        Product saved = productRepository.save(new Product("Headphones", 79.99, 30));
        productRepository.deleteById(saved.getId());

        Optional<Product> found = productRepository.findById(saved.getId());

        assertFalse(found.isPresent());
    }
}
