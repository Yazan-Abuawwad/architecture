package com.awwad.example.architecture.hexagonal.application.port.out;

import com.awwad.example.architecture.hexagonal.domain.Product;

import java.util.Optional;

public interface LoadProductPort {

    Optional<Product> findById(Long productId);
}

