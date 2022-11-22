package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.OrderEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderEntryRepository extends PagingAndSortingRepository<OrderEntry, Long>, CrudRepository<OrderEntry, Long> {
}
