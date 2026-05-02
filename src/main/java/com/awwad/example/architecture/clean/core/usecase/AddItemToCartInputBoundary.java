package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Cart;

public interface AddItemToCartInputBoundary {

    Cart execute(Long productId, Integer quantity);
}

