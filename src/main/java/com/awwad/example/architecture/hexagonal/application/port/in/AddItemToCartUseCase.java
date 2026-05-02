package com.awwad.example.architecture.hexagonal.application.port.in;

import com.awwad.example.architecture.hexagonal.domain.Cart;

public interface AddItemToCartUseCase {

    Cart addItem(Long productId, Integer quantity);
}

