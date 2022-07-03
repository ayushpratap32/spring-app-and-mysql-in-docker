package com.retail.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.orderservice.model.Order;
import com.retail.orderservice.model.OrderStatus;
import com.retail.orderservice.repository.ProductRepository;
import com.retail.orderservice.service.OrderService;
import com.retail.orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderServiceImpl orderService;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void processOrder_success_when_order_is_invalid() throws Exception {

        when(orderService.processOrder("dsaf")).thenReturn("Invalid Order Id");
        RequestBuilder requestBuilder= MockMvcRequestBuilders.request(HttpMethod.POST,"/orders/processing/{id}","dsaf");
        MvcResult mvcResult=mockMvc.perform(requestBuilder).andReturn();
        assertEquals("Invalid Order Id",mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }


    @Test
    void processOrder_success_when_order_is_processed() throws Exception {

        when(orderService.processOrder("dsaf")).thenReturn("Your order with order id dsaf is APPROVED");
        RequestBuilder requestBuilder= MockMvcRequestBuilders.request(HttpMethod.POST,"/orders/processing/{id}","dsaf");
        MvcResult mvcResult=mockMvc.perform(requestBuilder).andReturn();
        assertEquals("Your order with order id dsaf is APPROVED",mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }


    @Test
    void getAllOrders() throws Exception {

        List<Order> orders = Arrays.asList(new Order("a", "d", 2, OrderStatus.RECEIVED, LocalDateTime.now(), LocalDateTime.now()),
                new Order("a", "d", 2, OrderStatus.RECEIVED, LocalDateTime.now(), LocalDateTime.now()));
        when(orderService.getAllOrders("0","2")).thenReturn(new PageImpl<Order>(orders));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, "/orders/");
        MvcResult mvcResult=mockMvc.perform(request).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(),objectMapper.writeValueAsString(orders));

        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}