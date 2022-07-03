package com.retail.orderservice.service;

import com.retail.orderservice.dto.ProductDto;
import com.retail.orderservice.model.Order;
import com.retail.orderservice.model.OrderStatus;
import com.retail.orderservice.model.Product;
import com.retail.orderservice.repository.OrderRepository;
import com.retail.orderservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(value = {MockitoExtension.class})
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    ProductServiceImpl productService;


    @Test
    void getAllProducts() {
        PageRequest pageRequest=PageRequest.of(0,1);
        when(productRepository.findAll(pageRequest)).thenReturn(null);
    productService.getAllProducts("0","1");
    verify(productRepository,times(1)).findAll(pageRequest);
    }

    @Test
    void gerProduct() {
        when(productRepository.findById(anyString())).thenReturn(Optional.of(new Product("aa", "apple", 2, new BigDecimal(200))));
        productService.getProduct(anyString());
        verify(productRepository,times(1)).findById(anyString());

    }

    @Test
    void addProduct() {
        ProductDto productDto=new ProductDto("aa", "apple", 2, new BigDecimal(200));
        String productId="aa";
        when(orderRepository.save(any())).thenReturn(new Order());
        ProductService productService1=new ProductServiceImpl(productRepository,orderRepository,new ModelMapper());
        String actual= productService1.addProduct(productDto, productId);
        verify(orderRepository,times(1)).save(any(Order.class));
        assertTrue(actual.startsWith("Your order with order id"));
    }

}