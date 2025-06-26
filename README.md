# Cache Optimization Service (Spring Boot + Redis + H2)

This microservice demonstrates real-world caching strategies using Spring Boot and Redis to improve application performance and reduce database load. It simulates cold vs warm access patterns and provides benchmarks to measure performance gain from cache optimization.

## Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Data JPA + H2 (in-memory DB)  
- Spring Cache with Redis  
- REST API for data access and benchmarking

## Features

- Cache-aside strategy using `@Cacheable`  
- Cache invalidation with `@CacheEvict`  
- Simulated slow DB queries for cold start simulation  
- Redis TTL-based caching (configurable)  
- Manual and cached API paths  
- Benchmarking endpoint to measure performance difference  
- Layered architecture (Controller → Service → Repository)

## Caching Strategies Demonstrated

| Strategy             | Description                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| Cache-aside          | Uses `@Cacheable` to load data from DB only on first request                |
| Write-through        | Data is written to DB and Redis in update path (`@CacheEvict`)              |
| TTL (expiry)         | Set via config to auto-expire stale entries (if enabled in real Redis)      |
| Preload optional     | Can be extended to warm cache on startup (not active in this demo)          |
| Manual fallback      | `/nocache` endpoints simulate behavior before caching                       |

## API Endpoints

### Fetch Product with Cache  
`GET /api/products/{id}`  
→ Loads from Redis if available, otherwise fetches from DB.

### Fetch Product without Cache  
`GET /api/products/{id}/nocache`  
→ Always fetches from DB (simulates uncached cold path).

### Update Product  
`PUT /api/products/{id}`  
→ Saves to DB and clears cache entry for updated product.

### Benchmark  
`GET /api/benchmark/{id}?iterations=5`  
→ Compares cold (DB) vs warm (cached) access time over `n` iterations.

**Example Response:**  
`Avg Cold: 412.45 ms | Avg Warm: 210.87 ms | Gain: 48.86%`

## Configuration (Redis + H2)

Redis config (default):
```yaml
spring.cache.type: redis  
spring.redis.host: localhost  
spring.redis.port: 6379
```
