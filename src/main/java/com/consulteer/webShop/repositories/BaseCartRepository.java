package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.Cart;
import com.consulteer.webShop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseCartRepository extends PagingAndSortingRepository<Cart, Long>, CrudRepository<Cart, Long> {
}

