package com.awwad.example.architecture.hexagonal.adapter.in.web;

import com.awwad.example.architecture.hexagonal.application.port.in.AddItemToCartUseCase;
import com.awwad.example.architecture.hexagonal.application.port.in.GetCartUseCase;
import com.awwad.example.architecture.hexagonal.domain.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hexagonal/cart")
public class ShoppingCartController {

    private final AddItemToCartUseCase addItemToCartUseCase;
    private final GetCartUseCase getCartUseCase;

    public ShoppingCartController(AddItemToCartUseCase addItemToCartUseCase, GetCartUseCase getCartUseCase) {
        this.addItemToCartUseCase = addItemToCartUseCase;
        this.getCartUseCase = getCartUseCase;
    }

    @PostMapping("/items")
    public Cart addItem(@RequestBody AddToCartRequest request) {
        return addItemToCartUseCase.addItem(request.productId(), request.quantity());
    }

    @GetMapping
    public Cart getCart() {
        return getCartUseCase.getCart();
    }
}

