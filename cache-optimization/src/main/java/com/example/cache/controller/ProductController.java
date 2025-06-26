package com.example.cache.controller;

import com.example.cache.entity.Product;
import com.example.cache.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Product getCached(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return service.updateProduct(product);
    }

    @GetMapping("/{id}/nocache")
    public Product getWithoutCache(@PathVariable Long id) {
        return service.getProductWithoutCache(id);
    }
}