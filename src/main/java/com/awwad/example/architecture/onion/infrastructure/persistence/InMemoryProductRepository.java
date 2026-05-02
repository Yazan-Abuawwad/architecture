package com.awwad.example.architecture.onion.infrastructure.persistence;

import com.awwad.example.architecture.onion.domain.model.Product;
import com.awwad.example.architecture.onion.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> catalog = Map.of(
            1L, new Product(1L, "Laptop", new BigDecimal("1000.00"), 10, 5),
            2L, new Product(2L, "Mouse", new BigDecimal("25.00"), 0, 20),
            3L, new Product(3L, "Keyboard", new BigDecimal("75.00"), 5, 10)
    );

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(catalog.get(productId));
    }
}

