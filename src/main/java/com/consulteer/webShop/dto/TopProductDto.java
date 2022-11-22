package com.consulteer.webShop.dto;

import com.consulteer.webShop.enums.ProductStockStatus;

public record TopProductDto(String name, Double price, ProductStockStatus labelStock) {
}
