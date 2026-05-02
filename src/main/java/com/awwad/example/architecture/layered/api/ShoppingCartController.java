package com.awwad.example.architecture.layered.api;

import com.awwad.example.architecture.layered.domain.Cart;
import com.awwad.example.architecture.layered.service.ShoppingCartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/layered/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/items")
    public Cart addItem(@RequestBody AddToCartRequestDTO request) {
        return shoppingCartService.addItem(request.productId(), request.quantity());
    }

    @GetMapping
    public Cart getCart() {
        return shoppingCartService.getCart();
    }
}

