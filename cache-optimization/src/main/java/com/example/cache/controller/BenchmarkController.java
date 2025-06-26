package com.example.cache.controller;

import com.example.cache.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benchmark")
public class BenchmarkController {

    private final ProductService service;

    public BenchmarkController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String benchmark(@PathVariable Long id, @RequestParam(defaultValue = "5") int iterations) {
        long totalCold = 0, totalWarm = 0;

        for (int i = 0; i < iterations; i++) {
            long startCold = System.nanoTime();
            service.getProductWithoutCache(id);
            totalCold += System.nanoTime() - startCold;

            long startWarm = System.nanoTime();
            service.getProduct(id);
            totalWarm += System.nanoTime() - startWarm;
        }

        double avgCold = totalCold / (1_000_000.0 * iterations);
        double avgWarm = totalWarm / (1_000_000.0 * iterations);
        double gain = ((avgCold - avgWarm) / avgCold) * 100.0;

        return String.format("Avg Cold: %.2f ms | Avg Warm: %.2f ms | Gain: %.2f%%", avgCold, avgWarm, gain);
    }
}