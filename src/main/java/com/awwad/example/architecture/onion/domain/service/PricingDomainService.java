package com.awwad.example.architecture.onion.domain.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PricingDomainService {

    public BigDecimal applyDiscount(BigDecimal unitPrice, int discountPercent) {
        BigDecimal discountFactor = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return unitPrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }
}

