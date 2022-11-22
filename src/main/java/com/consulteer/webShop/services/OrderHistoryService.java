package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.OrderHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface OrderHistoryService {
    Page<OrderHistoryDto> getOrderHistory(String username, Pageable pageable);
    Page<OrderHistoryDto> getOrderHistoryWithTimeInterval(String username, Pageable pageable, Date from, Date to);
}
