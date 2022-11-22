package com.consulteer.webShop.model;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderEntry {

    @EmbeddedId
    private OrderProductPK orderProductPK;
    @ManyToOne
    @MapsId("order_id")
    private Order order;
    @ManyToOne
    @MapsId("product_id")
    private Product product;

    private Integer amount;

    public OrderEntry() {
    }

    public OrderProductPK getOrderProductPK() {
        return orderProductPK;
    }

    public void setOrderProductPK(OrderProductPK orderProductPK) {
        this.orderProductPK = orderProductPK;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
