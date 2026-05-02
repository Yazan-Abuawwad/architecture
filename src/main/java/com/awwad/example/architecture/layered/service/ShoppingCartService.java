package com.awwad.example.architecture.layered.service;

import com.awwad.example.architecture.layered.domain.Cart;
import com.awwad.example.architecture.layered.domain.CartItem;
import com.awwad.example.architecture.layered.domain.Product;
import com.awwad.example.architecture.layered.repository.CartRepository;
import com.awwad.example.architecture.layered.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ShoppingCartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ShoppingCartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public Cart addItem(Long productId, Integer quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("productId is required");
        }

        int normalizedQuantity = quantity == null ? 0 : quantity;
        if (normalizedQuantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        if (!productRepository.reserveStock(productId, normalizedQuantity)) {
            throw new IllegalArgumentException("insufficient stock");
        }

        BigDecimal discountedUnitPrice = applyDiscount(product.unitPrice(), product.discountPercent());
        BigDecimal lineTotal = discountedUnitPrice
                .multiply(BigDecimal.valueOf(normalizedQuantity))
                .setScale(2, RoundingMode.HALF_UP);

        CartItem item = new CartItem(
                product.id(),
                product.name(),
                normalizedQuantity,
                product.unitPrice(),
                discountedUnitPrice,
                lineTotal
        );
        return cartRepository.addOrUpdate(item);
    }

    public Cart getCart() {
        return cartRepository.getCart();
    }

    private BigDecimal applyDiscount(BigDecimal unitPrice, int discountPercent) {
        BigDecimal discountFactor = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return unitPrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }
}

