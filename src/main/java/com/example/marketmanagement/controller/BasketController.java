package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.BasketDto;
import com.example.marketmanagement.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @Operation(summary = "Get basket by ID", description = "Returns the basket details for the given basket ID.")
    @GetMapping("/{basketId}")
    public BasketDto getBasketById(@PathVariable Long basketId) {
        return basketService.getBasketById(basketId);
    }

    @Operation(summary = "Create a new basket", description = "Creates a new basket for the given user ID.")
    @PostMapping
    public void createBasket(@RequestParam Long userId) {
        basketService.createBasket(userId);
    }

    @PostMapping("/{basketId}")
    @Operation(summary = "Add product to basket", description = "Adds a product with a specified quantity to the basket.")
    public void addProductToBasket(
            @PathVariable Long basketId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        basketService.addProductToBasket(basketId, productId, quantity);
    }

    @PutMapping("/{basketId}")
    @Operation(summary = "Remove product from basket", description = "Removes a specified product from the basket.")
    public void removeProductFromBasket(
            @PathVariable Long basketId,
            @RequestParam Long productId
    ) {
        basketService.removeProductFromBasket(basketId, productId);
    }
}
