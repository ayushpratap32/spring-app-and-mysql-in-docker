package com.retail.orderservice.service;

import com.retail.orderservice.model.Order;
import com.retail.orderservice.model.OrderStatus;
import com.retail.orderservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final int AVAILABLE_QTY=1;

    @Override
    public String processOrder(String orderId) {
        Optional<Order> order=orderRepository.findById(orderId);
        if(order.isPresent()) {
            if (order.get().getOrderStatus()==OrderStatus.RECEIVED) {
                //check for order quantity vs available quantity
                if (order.get().getQuantity() <= AVAILABLE_QTY) {
                    order.get().setOrderStatus(OrderStatus.APPROVED);
                } else {
                    order.get().setOrderStatus(OrderStatus.REJECTED);
                }
                order.get().setLastUpdated(LocalDateTime.now());
                orderRepository.save(order.get());
            }
            return "Your order with order id " + orderId + " is " + order.get().getOrderStatus();
        }
        else{
            return "Invalid OrderID ";
        }
    }

    @Override
    public Page<Order> getAllOrders(String page, String size) {
        PageRequest pageRequest=PageRequest.of(Integer.parseInt(page),Integer.parseInt(size));
        return orderRepository.findAll(pageRequest);
    }
}
