package com.awwad.example.architecture.onion.domain.repository;

public interface StockRepository {

    boolean reserveStock(Long productId, int quantity);
}

