package com.awwad.example.architecture.clean.core.entity;

import java.math.BigDecimal;

public record CartItem(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal discountedUnitPrice,
        BigDecimal lineTotal
) {
}

