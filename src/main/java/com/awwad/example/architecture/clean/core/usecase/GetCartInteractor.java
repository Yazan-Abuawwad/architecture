package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Cart;
import com.awwad.example.architecture.clean.core.gateway.CartGateway;
import org.springframework.stereotype.Service;

@Service
public class GetCartInteractor implements GetCartInputBoundary {

    private final CartGateway cartGateway;

    public GetCartInteractor(CartGateway cartGateway) {
        this.cartGateway = cartGateway;
    }

    @Override
    public Cart execute() {
        return cartGateway.getCart();
    }
}

