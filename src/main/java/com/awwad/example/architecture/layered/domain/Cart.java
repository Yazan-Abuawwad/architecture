package com.awwad.example.architecture.layered.domain;

import java.math.BigDecimal;
import java.util.List;

public record Cart(List<CartItem> items, BigDecimal total) {
}

