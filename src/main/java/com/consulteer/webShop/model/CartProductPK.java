package com.consulteer.webShop.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartProductPK implements Serializable {
    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "product_id")
    private Long productId;

    public CartProductPK() {
    }

    public CartProductPK(Long cartId, Long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }
}
