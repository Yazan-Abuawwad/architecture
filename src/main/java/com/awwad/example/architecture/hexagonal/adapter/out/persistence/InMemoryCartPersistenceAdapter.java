package com.awwad.example.architecture.hexagonal.adapter.out.persistence;

import com.awwad.example.architecture.hexagonal.application.port.out.LoadCartPort;
import com.awwad.example.architecture.hexagonal.application.port.out.SaveCartPort;
import com.awwad.example.architecture.hexagonal.domain.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class InMemoryCartPersistenceAdapter implements LoadCartPort, SaveCartPort {

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


