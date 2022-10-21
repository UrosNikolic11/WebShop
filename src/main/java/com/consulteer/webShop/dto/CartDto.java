package com.consulteer.webShop.dto;

import java.util.List;

public class CartDto {
    private List<ShowProductDto> products;
    private Double totalPrice;

    public CartDto() {
    }

    public CartDto(List<ShowProductDto> products, Double totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public List<ShowProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ShowProductDto> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
