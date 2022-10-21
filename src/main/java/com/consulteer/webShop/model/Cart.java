package com.consulteer.webShop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartEntry> cartProducts;

    public Cart() {
    }

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Cart(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartEntry> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<CartEntry> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Double calculateTotalPrice() {
        return this.cartProducts.stream().map(entry ->
                entry.getAmount() * entry.getProduct().getPrice()
        ).mapToDouble(Double::doubleValue).reduce(0, Double::sum);
    }
}
