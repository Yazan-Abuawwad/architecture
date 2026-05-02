package com.awwad.example.architecture.clean.core.entity;

import java.math.BigDecimal;

public record Product(Long id, String name, BigDecimal unitPrice, int discountPercent, int availableStock) {
}

