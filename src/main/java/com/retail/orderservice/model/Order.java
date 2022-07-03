package com.retail.orderservice.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Order {

    @Id
    private String orderId;

    private String productId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;
    public Order() {
    }

    public Order(String orderId, String productId, int quantity, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", orderStatus=" + orderStatus +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
