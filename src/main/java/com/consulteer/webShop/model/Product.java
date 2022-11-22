package com.consulteer.webShop.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "item")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer numberInStock;
    @CreationTimestamp
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<CartEntry> cartProducts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<OrderEntry> orderEntries;

    public Product() {
    }

    public Product(Long id, String name, Double price, Integer numberInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberInStock = numberInStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(Integer numberInStock) {
        this.numberInStock = numberInStock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<CartEntry> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<CartEntry> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Set<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(Set<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, numberInStock, cartProducts);
    }
}
