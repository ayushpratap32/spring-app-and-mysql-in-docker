package com.retail.orderservice.service;

import com.retail.orderservice.dto.ProductDto;
import com.retail.orderservice.model.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    public abstract Page<Product> getAllProducts(String page, String size);


    Optional<Product> getProduct(String productId);

    public abstract String addProduct(ProductDto productDto, String productId);

}
