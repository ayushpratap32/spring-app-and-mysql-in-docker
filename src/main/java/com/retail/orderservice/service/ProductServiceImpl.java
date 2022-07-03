package com.retail.orderservice.service;

import com.retail.orderservice.dto.ProductDto;
import com.retail.orderservice.model.Order;
import com.retail.orderservice.model.OrderStatus;
import com.retail.orderservice.model.Product;
import com.retail.orderservice.repository.OrderRepository;
import com.retail.orderservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<Product> getAllProducts(String page, String size) {
        PageRequest pageRequest=PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Product> getProduct(String productId) {
        return productRepository.findById(productId);

    }

    @Override
    public String addProduct(ProductDto productDto, String productId) {

        Product product= toEntity(productDto);
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId,product.getProductId(),product.getQuantity(), OrderStatus.RECEIVED, LocalDateTime.now(),LocalDateTime.now());
         orderRepository.save(order);
        return "Your order with order id "+orderId+" is received and in progress.";
    }

    private Product toEntity(ProductDto productDto){
        Product product=new Product();
        modelMapper.map(productDto,product);
        return product;
    }
}
