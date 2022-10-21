package com.consulteer.webShop.model;

import javax.persistence.*;

@Entity
@Table(name = "cart_product")
public class CartEntry {
    @EmbeddedId
    private CartProductPK cartProductPK;
    @ManyToOne
    @MapsId("cart_id")
    private Cart cart;
    @ManyToOne
    @MapsId("product_id")
    private Product product;
    @Column(nullable = false)
    private Integer amount;

    public CartEntry() {
    }

    public CartProductPK getCartProductPK() {
        return cartProductPK;
    }

    public void setCartProductPK(CartProductPK cartProductPK) {
        this.cartProductPK = cartProductPK;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
