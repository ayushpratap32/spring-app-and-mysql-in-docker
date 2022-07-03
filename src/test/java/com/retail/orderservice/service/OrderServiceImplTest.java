package com.retail.orderservice.service;

import com.retail.orderservice.model.Order;
import com.retail.orderservice.model.OrderStatus;
import com.retail.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void processOrder_when_Quantity_is_insufficient() {
        String orderId= UUID.randomUUID().toString();
        Order order=new Order(orderId,"aa",2, OrderStatus.RECEIVED, LocalDateTime.now(),LocalDateTime.now().minusDays(2l));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        String actual = orderService.processOrder(orderId);
        verify(orderRepository).save(any(Order.class));
        assertEquals(OrderStatus.REJECTED,order.getOrderStatus());
        assertTrue(actual.startsWith("Your order with order id"));

    }
    @Test
    void processOrder_when_Quantity_is_sufficient() {
        String orderId= UUID.randomUUID().toString();
        Order order=new Order(orderId,"aa",1, OrderStatus.RECEIVED, LocalDateTime.now(),LocalDateTime.now().minusDays(2l));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        String actual = orderService.processOrder(orderId);
        verify(orderRepository).save(any(Order.class));
        assertTrue(actual.startsWith("Your order with order id"));
        assertEquals(OrderStatus.APPROVED,order.getOrderStatus());

    }


    @Test
    void processOrder_when_Order_Is_Already_Processed() {
        String orderId= UUID.randomUUID().toString();
        Order order=new Order(orderId,"aa",2, OrderStatus.REJECTED, LocalDateTime.now(),LocalDateTime.now().minusDays(2l));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        String actual = orderService.processOrder(orderId);
        verify(orderRepository,never()).save(any(Order.class));
        assertEquals(OrderStatus.REJECTED,order.getOrderStatus());
        assertTrue(actual.startsWith("Your order with order id"));

    }
    @Test
    void processOrder_when_Order_Not_Found() {
        String orderId= UUID.randomUUID().toString();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        String actual = orderService.processOrder(orderId);
        verify(orderRepository,never()).save(any(Order.class));
        assertTrue(actual.startsWith("Invalid OrderID "));


    }

    @Test
    void getAllOrders() {
        PageRequest pageRequest=PageRequest.of(0,2);
        when(orderRepository.findAll(pageRequest)).thenReturn(new PageImpl<Order>(Arrays.asList(new Order(),new Order())));
        orderService.getAllOrders("0","2");
        verify(orderRepository,times(1)).findAll(pageRequest);

    }
}