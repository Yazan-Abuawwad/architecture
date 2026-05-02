package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Cart;
import com.awwad.example.architecture.clean.core.entity.CartItem;
import com.awwad.example.architecture.clean.core.entity.Product;
import com.awwad.example.architecture.clean.core.gateway.CartGateway;
import com.awwad.example.architecture.clean.core.gateway.ProductGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AddItemToCartInteractor implements AddItemToCartInputBoundary {

    private final ProductGateway productGateway;
    private final CartGateway cartGateway;

    public AddItemToCartInteractor(ProductGateway productGateway, CartGateway cartGateway) {
        this.productGateway = productGateway;
        this.cartGateway = cartGateway;
    }

    @Override
    public Cart execute(Long productId, Integer quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("productId is required");
        }

        int normalizedQuantity = quantity == null ? 0 : quantity;
        if (normalizedQuantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        Product product = productGateway.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        if (!productGateway.reserveStock(productId, normalizedQuantity)) {
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
        return cartGateway.addOrUpdate(item);
    }

    private BigDecimal applyDiscount(BigDecimal unitPrice, int discountPercent) {
        BigDecimal discountFactor = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return unitPrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }
}

