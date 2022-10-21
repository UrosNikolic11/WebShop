package com.consulteer.webShop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class CreateProductDto {
    @NotBlank
    private final String name;
    @NotNull
    private final Double price;
    @NotNull
    private final Integer numberInStock;

    public CreateProductDto(String name, Double price, Integer numberInStock) {
        this.name = name;
        this.price = price;
        this.numberInStock = numberInStock;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getNumberInStock() {
        return numberInStock;
    }
}
