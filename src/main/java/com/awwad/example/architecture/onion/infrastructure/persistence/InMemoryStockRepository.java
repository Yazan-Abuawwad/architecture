package com.awwad.example.architecture.onion.infrastructure.persistence;

import com.awwad.example.architecture.onion.domain.repository.StockRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryStockRepository implements StockRepository {

    private final Map<Long, Integer> stockByProductId = new HashMap<>();

    public InMemoryStockRepository() {
        stockByProductId.put(1L, 5);
        stockByProductId.put(2L, 20);
        stockByProductId.put(3L, 10);
    }

    @Override
    public synchronized boolean reserveStock(Long productId, int quantity) {
        Integer available = stockByProductId.get(productId);
        if (available == null || quantity <= 0 || available < quantity) {
            return false;
        }
        stockByProductId.put(productId, available - quantity);
        return true;
    }
}

