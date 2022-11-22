package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.OrderHistoryDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.mappers.OrderHistoryMapper;
import com.consulteer.webShop.model.User;
import com.consulteer.webShop.repositories.OrderRepository;
import com.consulteer.webShop.repositories.UserRepository;
import com.consulteer.webShop.services.OrderHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderHistoryMapper orderHistoryMapper;

    public OrderHistoryServiceImpl(OrderRepository orderRepository, UserRepository userRepository, OrderHistoryMapper orderHistoryMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    @Override
    public Page<OrderHistoryDto> getOrderHistory(String username, Pageable pageable) {
        return orderRepository
                .getOrdersByUserOrderByCreatedAtDesc(userRepository
                        .findUserByUsername(username).orElseThrow(() -> new BadRequestException("User not found!")), pageable)
                .map(orderHistoryMapper::map);
    }

    @Override
    public Page<OrderHistoryDto> getOrderHistoryWithTimeInterval(String username, Pageable pageable, Date from, Date to) {
      User user = userRepository
                .findUserByUsername(username).orElseThrow(() -> new BadRequestException("User not found!"));

        return orderRepository
                .getOrdersByUserAndTimeInterval(user.getId(), pageable, from, to)
                .map(orderHistoryMapper::map);
    }
}
