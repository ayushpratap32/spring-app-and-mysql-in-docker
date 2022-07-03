package com.retail.orderservice.service;

import com.retail.orderservice.model.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    public abstract String processOrder(String orderId);

    public abstract Page<Order> getAllOrders(String page, String size);
}

