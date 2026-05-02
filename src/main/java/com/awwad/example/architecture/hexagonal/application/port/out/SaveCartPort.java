package com.awwad.example.architecture.hexagonal.application.port.out;

import com.awwad.example.architecture.hexagonal.domain.Cart;

public interface SaveCartPort {

    Cart save(Cart cart);
}

