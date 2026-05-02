package com.awwad.example.architecture.layered.repository;

import com.awwad.example.architecture.layered.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    boolean reserveStock(Long productId, int quantity);
}

