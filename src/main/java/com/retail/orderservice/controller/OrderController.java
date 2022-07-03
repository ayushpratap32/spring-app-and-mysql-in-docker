package com.retail.orderservice.controller;

import com.retail.orderservice.model.Order;
import com.retail.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/processing/{id}")
    public ResponseEntity<String> processOrder(@PathVariable("id") String orderId){
        String responseBody = orderService.processOrder(orderId);
        return new ResponseEntity<>(responseBody,
                null,
                HttpStatus.OK);
    }

    @GetMapping("/")
    public List<Order> getAllOrders(@RequestParam(name="page",defaultValue = "0") String page, @RequestParam(name="size",defaultValue = "2") String size){
       return orderService.getAllOrders(page,size).getContent();
    }
}
