package com.awwad.example.architecture.layered.api;

public record AddToCartRequestDTO(Long productId, Integer quantity) {
    //todo NOTE Records are better here because request DTOs are just data carriers
    //   Immutability (fields are final)
    //   equals, hashCode, toString auto-generated
    //   Compact, readable syntax
}

