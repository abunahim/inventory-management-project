package com.inventory.inventory_management.service;

import com.inventory.inventory_management.dto.ProductRequestDTO;
import com.inventory.inventory_management.dto.ProductResponseDTO;
import com.inventory.inventory_management.model.Product;
import com.inventory.inventory_management.repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, ProductResponseDTO> redisTemplate;

    private static final String CACHE_PREFIX = "product::";

    public ProductService(ProductRepository productRepository,
                          RedisTemplate<String, ProductResponseDTO> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    // ---- Conversion Helpers ----

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    // ---- Service Methods ----

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO saveProduct(ProductRequestDTO dto) {
        Product saved = productRepository.save(toEntity(dto));
        return toResponseDTO(saved);
    }

    public ProductResponseDTO getProductById(Long id) {
        String key = CACHE_PREFIX + id;

        // Check Redis first
        ProductResponseDTO cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            System.out.println(">>> Cache HIT for id: " + id);
            return cached;
        }

        // Cache miss — fetch from DB
        System.out.println(">>> Fetching from DATABASE for id: " + id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        ProductResponseDTO dto = toResponseDTO(product);

        // Store in Redis for 10 minutes
        redisTemplate.opsForValue().set(key, dto, Duration.ofMinutes(10));

        return dto;
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        ProductResponseDTO updated = toResponseDTO(productRepository.save(product));

        // Update cache
        redisTemplate.opsForValue().set(CACHE_PREFIX + id, updated, Duration.ofMinutes(10));

        return updated;
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.deleteById(id);

        // Remove from cache
        redisTemplate.delete(CACHE_PREFIX + id);
    }
}