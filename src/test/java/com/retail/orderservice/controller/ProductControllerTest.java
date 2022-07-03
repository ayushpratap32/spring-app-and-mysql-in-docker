package com.retail.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.orderservice.dto.ProductDto;
import com.retail.orderservice.model.Product;
import com.retail.orderservice.repository.OrderRepository;
import com.retail.orderservice.repository.ProductRepository;
import com.retail.orderservice.service.ProductService;
import com.retail.orderservice.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ProductControllerTest {

    @MockBean
    ProductServiceImpl productService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderRepository orderRepository;


    @Test
    void getAllProducts() throws Exception {
        List<Product> products= Arrays.asList(new Product("cc2022","Chair",2,new BigDecimal("250")),
                new Product("dd2022","Duster",2,new BigDecimal("9250"))  );
        assertNotNull(productService);
        when(productService.getAllProducts("0","2")).thenReturn(new PageImpl<Product>(products));
        RequestBuilder requestBuilder= MockMvcRequestBuilders.request(HttpMethod.GET,"/products");
        //MvcResult mvcResult =
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.*",hasSize(2)))
                        .andExpect(jsonPath("$[0].*",hasSize(4)))
                        .andExpect(jsonPath("$[0].productTitle").value("Chair"))
                        .andExpect(jsonPath("$[1].productTitle").value("Duster"));//.andReturn();

        //assertEquals(mvcResult.getResponse().getContentAsString(),objectMapper.writeValueAsString(products));
        //assertEquals(200,mvcResult.getResponse().getStatus());
        //assertEquals(mvcResult,products.size());
    }

    @Test
    void getProduct() throws Exception {
        Product product = new Product("dd2022", "Duster", 2, new BigDecimal("9250"));
        when(productService.getProduct(anyString())).thenReturn(Optional.of(product));
        RequestBuilder requestBuilder=MockMvcRequestBuilders.request(HttpMethod.GET,"/products/{id}","dd2022");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(),product.toString());
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getProduct_but_actual_not_present() throws Exception {
        when(productService.getProduct(anyString())).thenReturn(Optional.empty());
        RequestBuilder requestBuilder=MockMvcRequestBuilders.request(HttpMethod.GET,"/products/{id}","dd2022");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("Invalid Product Id",mvcResult.getResponse().getContentAsString());
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void addProduct() throws Exception {
        String productId="aab";
        ProductDto productDto=new ProductDto("aab", "apple", 2, new BigDecimal(200));
        when(productService.addProduct(any(ProductDto.class),any(String.class))).thenReturn("Your order with order id xyz is received and in progress.");
        RequestBuilder requestBuilder=MockMvcRequestBuilders.request(HttpMethod.POST,"/products/{id}","aab")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        verify(productService,times(1)).addProduct(any(ProductDto.class),any(String.class));
        assertEquals("Your order with order id xyz is received and in progress.",mvcResult.getResponse().getContentAsString());
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}