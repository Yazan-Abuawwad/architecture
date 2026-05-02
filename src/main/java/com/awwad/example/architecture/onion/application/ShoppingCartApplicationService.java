package com.awwad.example.architecture.onion.application;

import com.awwad.example.architecture.onion.domain.model.Cart;
import com.awwad.example.architecture.onion.domain.model.CartItem;
import com.awwad.example.architecture.onion.domain.model.Product;
import com.awwad.example.architecture.onion.domain.repository.CartRepository;
import com.awwad.example.architecture.onion.domain.repository.ProductRepository;
import com.awwad.example.architecture.onion.domain.repository.StockRepository;
import com.awwad.example.architecture.onion.domain.service.PricingDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartApplicationService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final CartRepository cartRepository;
    private final PricingDomainService pricingDomainService;

    public ShoppingCartApplicationService(
            ProductRepository productRepository,
            StockRepository stockRepository,
            CartRepository cartRepository,
            PricingDomainService pricingDomainService
    ) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.cartRepository = cartRepository;
        this.pricingDomainService = pricingDomainService;
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

        if (!stockRepository.reserveStock(productId, normalizedQuantity)) {
            throw new IllegalArgumentException("insufficient stock");
        }

        BigDecimal discountedUnitPrice = pricingDomainService.applyDiscount(product.unitPrice(), product.discountPercent());
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

        Cart current = cartRepository.load();
        Cart updated = mergeCartItem(current, incoming);
        return cartRepository.save(updated);
    }

    public Cart getCart() {
        return cartRepository.load();
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

