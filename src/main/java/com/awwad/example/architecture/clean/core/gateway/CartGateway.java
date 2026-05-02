package com.awwad.example.architecture.clean.core.gateway;

import com.awwad.example.architecture.clean.core.entity.Cart;
import com.awwad.example.architecture.clean.core.entity.CartItem;

public interface CartGateway {

    Cart addOrUpdate(CartItem item);

    Cart getCart();
}

