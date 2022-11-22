package com.consulteer.webShop.dto;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

public class OrderHistoryDto {
    private Double totalPrice;
    private String address;
    private String number;
    private String city;
    private Date createdAt;
    private List<ShowProductDto> orderEntries;



    public OrderHistoryDto(Double totalPrice, String address, String number, String city, Date createdAt, List<ShowProductDto> orderEntries) {
        this.totalPrice = totalPrice;
        this.address = address;
        this.number = number;
        this.city = city;
        this.createdAt = createdAt;
        this.orderEntries = orderEntries;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ShowProductDto> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<ShowProductDto> orderEntries) {
        this.orderEntries = orderEntries;
    }
}
