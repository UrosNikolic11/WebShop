package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.Cart;
import com.consulteer.webShop.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository {
    void create(User user);
    Optional<Cart> findById(Long id);
}
