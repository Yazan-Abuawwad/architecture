package com.awwad.example.architecture.onion.domain.repository;

import com.awwad.example.architecture.onion.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);
}

