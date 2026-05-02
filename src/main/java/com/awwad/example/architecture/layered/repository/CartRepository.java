package com.awwad.example.architecture.layered.repository;

import com.awwad.example.architecture.layered.domain.Cart;
import com.awwad.example.architecture.layered.domain.CartItem;

public interface CartRepository {

    Cart addOrUpdate(CartItem item);

    Cart getCart();
}

