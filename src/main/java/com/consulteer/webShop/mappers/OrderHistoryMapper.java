package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.OrderHistoryDto;
import com.consulteer.webShop.dto.ShowProductDto;
import com.consulteer.webShop.model.Order;
import com.consulteer.webShop.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderHistoryMapper {

    public OrderHistoryMapper() {
    }

    public OrderHistoryDto map(Order order) {
        List<ShowProductDto> showProductDtoList = new ArrayList<>();

        order.getOrderEntries().forEach(orderEntry -> {

            Product product = orderEntry.getProduct();
            ShowProductDto showProductDto = new ShowProductDto(product.getId(), product.getName(), product.getPrice(), orderEntry.getAmount());
            showProductDtoList.add(showProductDto);

        });

        return new OrderHistoryDto(order.getTotalPrice(),
                order.getAddress(), order.getNumber(), order.getCity(), order.getCreatedAt(), showProductDtoList);
    }
}
