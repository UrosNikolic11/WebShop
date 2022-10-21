package com.consulteer.webShop.dto;

import com.consulteer.webShop.model.CartEntry;

public record ShowProductDto(Long id, String name, Double price, Integer amount) {

    public static ShowProductDto map(CartEntry cartEntry) {
        return new ShowProductDto(cartEntry.getProduct().getId(), cartEntry.getProduct().getName(),
                cartEntry.getProduct().getPrice(), cartEntry.getAmount());
    }
}
