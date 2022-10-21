package com.consulteer.webShop.dto;

public record CartEntryDto(Long cartId, Long productId, Integer amount) {
}
