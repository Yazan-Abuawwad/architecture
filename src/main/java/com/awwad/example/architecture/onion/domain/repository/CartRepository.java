package com.awwad.example.architecture.onion.domain.repository;

import com.awwad.example.architecture.onion.domain.model.Cart;

public interface CartRepository {

    Cart load();

    Cart save(Cart cart);
}

