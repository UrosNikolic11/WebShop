package com.consulteer.webShop.controllers;

import com.consulteer.webShop.dto.*;
import com.consulteer.webShop.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addProductToCart(@RequestBody @Valid CartEntryDto cartEntryDto) {
        cartService.addProductToCart(cartEntryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable("id") Long id) {
        cartService.clearCart(id);
    }

    @PostMapping("/buy")
    public ResponseEntity<BuyDtoResponse> buy(@RequestBody @Valid BuyDto buyDto) {
        return new ResponseEntity<>(cartService.buy(buyDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> showCart(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartService.showCart(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeProductFromCart(@RequestBody @Valid CartEntryDto cartEntryDto) {
        cartService.removeProductFromCart(cartEntryDto);
    }
}
