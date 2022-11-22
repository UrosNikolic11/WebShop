package com.consulteer.webShop.repositories;

import com.consulteer.webShop.model.Order;
import com.consulteer.webShop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, CrudRepository<Order, Long> {
    Page<Order> getOrdersByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    @Query(value="SELECT o FROM Order o WHERE o.user.id = :id AND o.createdAt BETWEEN :from AND :to ORDER BY o.createdAt DESC")
    Page<Order> getOrdersByUserAndTimeInterval(Long id, Pageable pageable, Date from, Date to);
}
