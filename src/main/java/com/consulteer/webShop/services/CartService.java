package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.BuyDto;
import com.consulteer.webShop.dto.BuyDtoResponse;
import com.consulteer.webShop.dto.CartEntryDto;
import com.consulteer.webShop.dto.CartDto;

public interface CartService {
    void addProductToCart(CartEntryDto cartEntryDto);
    void removeProductFromCart(CartEntryDto cartEntryDto);
    CartDto showCart(Long id);
    void clearCart(Long id);
    BuyDtoResponse buy(BuyDto buyDto);
}
