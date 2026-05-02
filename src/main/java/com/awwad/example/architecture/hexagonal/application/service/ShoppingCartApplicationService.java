package com.awwad.example.architecture.hexagonal.application.service;

import com.awwad.example.architecture.hexagonal.application.port.in.AddItemToCartUseCase;
import com.awwad.example.architecture.hexagonal.application.port.in.GetCartUseCase;
import com.awwad.example.architecture.hexagonal.application.port.out.LoadCartPort;
import com.awwad.example.architecture.hexagonal.application.port.out.LoadProductPort;
import com.awwad.example.architecture.hexagonal.application.port.out.ReserveStockPort;
import com.awwad.example.architecture.hexagonal.application.port.out.SaveCartPort;
import com.awwad.example.architecture.hexagonal.domain.Cart;
import com.awwad.example.architecture.hexagonal.domain.CartItem;
import com.awwad.example.architecture.hexagonal.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartApplicationService implements AddItemToCartUseCase, GetCartUseCase {

    private final LoadProductPort loadProductPort;
    private final ReserveStockPort reserveStockPort;
    private final LoadCartPort loadCartPort;
    private final SaveCartPort saveCartPort;

    public ShoppingCartApplicationService(
            LoadProductPort loadProductPort,
            ReserveStockPort reserveStockPort,
            LoadCartPort loadCartPort,
            SaveCartPort saveCartPort
    ) {
        this.loadProductPort = loadProductPort;
        this.reserveStockPort = reserveStockPort;
        this.loadCartPort = loadCartPort;
        this.saveCartPort = saveCartPort;
    }

    @Override
    public Cart addItem(Long productId, Integer quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("productId is required");
        }

        int normalizedQuantity = quantity == null ? 0 : quantity;
        if (normalizedQuantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        Product product = loadProductPort.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        if (!reserveStockPort.reserveStock(productId, normalizedQuantity)) {
            throw new IllegalArgumentException("insufficient stock");
        }

        BigDecimal discountedUnitPrice = applyDiscount(product.unitPrice(), product.discountPercent());
        BigDecimal lineTotal = discountedUnitPrice
                .multiply(BigDecimal.valueOf(normalizedQuantity))
                .setScale(2, RoundingMode.HALF_UP);

        CartItem incoming = new CartItem(
                product.id(),
                product.name(),
                normalizedQuantity,
                product.unitPrice(),
                discountedUnitPrice,
                lineTotal
        );

        Cart current = loadCartPort.load();
        Cart updated = mergeCartItem(current, incoming);
        return saveCartPort.save(updated);
    }

    @Override
    public Cart getCart() {
        return loadCartPort.load();
    }

    private BigDecimal applyDiscount(BigDecimal unitPrice, int discountPercent) {
        BigDecimal discountFactor = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return unitPrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }

    private Cart mergeCartItem(Cart current, CartItem incoming) {
        List<CartItem> mergedItems = new ArrayList<>(current.items());
        for (int i = 0; i < mergedItems.size(); i++) {
            CartItem existing = mergedItems.get(i);
            if (existing.productId().equals(incoming.productId())) {
                int newQuantity = existing.quantity() + incoming.quantity();
                BigDecimal lineTotal = incoming.discountedUnitPrice()
                        .multiply(BigDecimal.valueOf(newQuantity))
                        .setScale(2, RoundingMode.HALF_UP);
                mergedItems.set(i, new CartItem(
                        incoming.productId(),
                        incoming.productName(),
                        newQuantity,
                        incoming.unitPrice(),
                        incoming.discountedUnitPrice(),
                        lineTotal
                ));
                return recalculateCart(mergedItems);
            }
        }

        mergedItems.add(incoming);
        return recalculateCart(mergedItems);
    }

    private Cart recalculateCart(List<CartItem> items) {
        BigDecimal total = items.stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return new Cart(List.copyOf(items), total);
    }
}

