package com.awwad.example.architecture.onion.infrastructure.persistence;

import com.awwad.example.architecture.onion.domain.model.Cart;
import com.awwad.example.architecture.onion.domain.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Repository
public class InMemoryCartRepository implements CartRepository {

    private Cart cart = new Cart(List.of(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

    @Override
    public synchronized Cart load() {
        return cart;
    }

    @Override
    public synchronized Cart save(Cart cart) {
        this.cart = cart;
        return this.cart;
    }
}

