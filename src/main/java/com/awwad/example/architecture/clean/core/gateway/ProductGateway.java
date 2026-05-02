package com.awwad.example.architecture.clean.core.gateway;

import com.awwad.example.architecture.clean.core.entity.Product;

import java.util.Optional;

public interface ProductGateway {

    Optional<Product> findById(Long productId);

    boolean reserveStock(Long productId, int quantity);
}

