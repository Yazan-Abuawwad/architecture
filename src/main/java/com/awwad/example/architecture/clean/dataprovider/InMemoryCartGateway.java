package com.awwad.example.architecture.clean.dataprovider;

import com.awwad.example.architecture.clean.core.entity.Cart;
import com.awwad.example.architecture.clean.core.entity.CartItem;
import com.awwad.example.architecture.clean.core.gateway.CartGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryCartGateway implements CartGateway {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    @Override
    public synchronized Cart addOrUpdate(CartItem incoming) {
        CartItem existing = items.get(incoming.productId());
        if (existing == null) {
            items.put(incoming.productId(), incoming);
            return getCart();
        }

        int newQuantity = existing.quantity() + incoming.quantity();
        BigDecimal lineTotal = incoming.discountedUnitPrice()
                .multiply(BigDecimal.valueOf(newQuantity))
                .setScale(2, RoundingMode.HALF_UP);

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
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return new Cart(List.copyOf(cartItems), total);
    }
}

