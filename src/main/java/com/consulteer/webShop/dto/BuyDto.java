package com.consulteer.webShop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record BuyDto(
        @NotNull
        Long cartId,
        @NotBlank
        String address,
        @NotBlank
        String number,
        @NotBlank
        String city) {
}
