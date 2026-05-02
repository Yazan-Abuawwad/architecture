package com.awwad.example.architecture.layered.repository;

import com.awwad.example.architecture.layered.domain.Cart;
import com.awwad.example.architecture.layered.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryCartRepository implements CartRepository {
// todo readme  In-memory repositories are used here for demo/learning purposes, not production. Specifically in this project:
//  The focus is architecture, not persistence

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    @Override
    public synchronized Cart addOrUpdate(CartItem incoming) {
        CartItem existing = items.get(incoming.productId());
        if (existing == null) {
            items.put(incoming.productId(), incoming);
            return getCart();
        }

        int newQuantity = existing.quantity() + incoming.quantity();
        BigDecimal lineTotal = incoming.discountedUnitPrice().multiply(BigDecimal.valueOf(newQuantity));
        CartItem merged = new CartItem(
                incoming.productId(),
                incoming.productName(),
                newQuantity,
                incoming.unitPrice(),
                incoming.discountedUnitPrice(),
                lineTotal
        );
        items.put(incoming.productId(), merged);
        return getCart();
    }

    @Override
    public synchronized Cart getCart() {
        List<CartItem> cartItems = new ArrayList<>(items.values());
        BigDecimal total = cartItems.stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Cart(List.copyOf(cartItems), total);
    }
}

