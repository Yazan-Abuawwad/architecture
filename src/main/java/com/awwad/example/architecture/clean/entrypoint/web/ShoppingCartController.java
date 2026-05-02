package com.awwad.example.architecture.clean.entrypoint.web;

import com.awwad.example.architecture.clean.core.entity.Cart;
import com.awwad.example.architecture.clean.core.usecase.AddItemToCartInputBoundary;
import com.awwad.example.architecture.clean.core.usecase.GetCartInputBoundary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clean/cart")
public class ShoppingCartController {

    private final AddItemToCartInputBoundary addItemToCart;
    private final GetCartInputBoundary getCart;

    public ShoppingCartController(AddItemToCartInputBoundary addItemToCart, GetCartInputBoundary getCart) {
        this.addItemToCart = addItemToCart;
        this.getCart = getCart;
    }

    @PostMapping("/items")
    public Cart addItem(@RequestBody AddToCartRequest request) {
        return addItemToCart.execute(request.productId(), request.quantity());
    }

    @GetMapping
    public Cart getCart() {
        return getCart.execute();
    }
}

