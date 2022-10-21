package com.consulteer.webShop.repositories.impl;

import com.consulteer.webShop.model.Cart;
import com.consulteer.webShop.model.User;
import com.consulteer.webShop.repositories.BaseCartRepository;
import com.consulteer.webShop.repositories.CartRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartRepositoryImpl implements CartRepository {
    private final BaseCartRepository baseCartRepository;

    public CartRepositoryImpl(BaseCartRepository baseCartRepository) {
        this.baseCartRepository = baseCartRepository;
    }

    @Override
    public void create(User user) {
        Cart cart = new Cart(user);
        baseCartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return baseCartRepository.findById(id);
    }

}
