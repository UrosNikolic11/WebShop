package com.consulteer.webShop.controllers;

import com.consulteer.webShop.dto.OrderHistoryDto;
import com.consulteer.webShop.services.OrderHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/history")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<Page<OrderHistoryDto>> getOrderHistory(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderHistoryService.getOrderHistory(username, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/withTimeInterval")
    public ResponseEntity<Page<OrderHistoryDto>> getOrderHistoryWithTimeInterval(@RequestParam(value = "from")
                                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                                                                 @RequestParam(value = "to")
                                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to,
                                                                                 Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(orderHistoryService.getOrderHistoryWithTimeInterval(username, pageable, from, to), HttpStatus.OK);
    }
}
