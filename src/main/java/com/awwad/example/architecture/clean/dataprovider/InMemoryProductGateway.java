package com.awwad.example.architecture.clean.dataprovider;

import com.awwad.example.architecture.clean.core.entity.Product;
import com.awwad.example.architecture.clean.core.gateway.ProductGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryProductGateway implements ProductGateway {

    private final Map<Long, InventoryProduct> catalog = new HashMap<>();

    public InMemoryProductGateway() {
        catalog.put(1L, new InventoryProduct(1L, "Laptop", new BigDecimal("1000.00"), 10, 5));
        catalog.put(2L, new InventoryProduct(2L, "Mouse", new BigDecimal("25.00"), 0, 20));
        catalog.put(3L, new InventoryProduct(3L, "Keyboard", new BigDecimal("75.00"), 5, 10));
    }

    @Override
    public synchronized Optional<Product> findById(Long productId) {
        InventoryProduct product = catalog.get(productId);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(product.toDomain());
    }

    @Override
    public synchronized boolean reserveStock(Long productId, int quantity) {
        InventoryProduct product = catalog.get(productId);
        if (product == null || quantity <= 0) {
            return false;
        }
        if (product.availableStock < quantity) {
            return false;
        }
        product.availableStock -= quantity;
        return true;
    }

    private static final class InventoryProduct {
        private final Long id;
        private final String name;
        private final BigDecimal unitPrice;
        private final int discountPercent;
        private int availableStock;

        private InventoryProduct(Long id, String name, BigDecimal unitPrice, int discountPercent, int availableStock) {
            this.id = id;
            this.name = name;
            this.unitPrice = unitPrice;
            this.discountPercent = discountPercent;
            this.availableStock = availableStock;
        }

        private Product toDomain() {
            return new Product(id, name, unitPrice, discountPercent, availableStock);
        }
    }
}

