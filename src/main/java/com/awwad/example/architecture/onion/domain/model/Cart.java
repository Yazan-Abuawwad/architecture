package com.awwad.example.architecture.onion.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record Cart(List<CartItem> items, BigDecimal total) {
}

