package com.awwad.example.architecture.onion.presentation;

import com.awwad.example.architecture.onion.application.ShoppingCartApplicationService;
import com.awwad.example.architecture.onion.domain.model.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onion/cart")
public class ShoppingCartController {

    private final ShoppingCartApplicationService shoppingCartApplicationService;

    public ShoppingCartController(ShoppingCartApplicationService shoppingCartApplicationService) {
        this.shoppingCartApplicationService = shoppingCartApplicationService;
    }

    @PostMapping("/items")
    public Cart addItem(@RequestBody AddItemToCartRequest request) {
        return shoppingCartApplicationService.addItem(request.productId(), request.quantity());
    }

    @GetMapping
    public Cart getCart() {
        return shoppingCartApplicationService.getCart();
    }
}

