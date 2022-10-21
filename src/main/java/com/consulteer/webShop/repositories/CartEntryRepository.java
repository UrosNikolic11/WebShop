package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.CartEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartEntryRepository extends PagingAndSortingRepository<CartEntry, Long>, CrudRepository<CartEntry, Long> {
}
