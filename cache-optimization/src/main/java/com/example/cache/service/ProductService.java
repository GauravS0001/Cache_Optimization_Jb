package com.example.cache.service;

import com.example.cache.entity.Product;
import com.example.cache.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {
        simulateSlowQuery();
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        return repo.save(product);
    }

    public Product getProductWithoutCache(Long id) {
        simulateSlowQuery();
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private void simulateSlowQuery() {
        try {
            Thread.sleep(400); // Simulate slow DB call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}