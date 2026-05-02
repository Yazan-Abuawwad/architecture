package com.awwad.example.architecture.hexagonal.application.port.out;

public interface ReserveStockPort {

    boolean reserveStock(Long productId, int quantity);
}

