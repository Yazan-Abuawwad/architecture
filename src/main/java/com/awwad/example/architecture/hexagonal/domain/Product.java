package com.awwad.example.architecture.hexagonal.domain;

import java.math.BigDecimal;

public record Product(Long id, String name, BigDecimal unitPrice, int discountPercent, int availableStock) {
}

