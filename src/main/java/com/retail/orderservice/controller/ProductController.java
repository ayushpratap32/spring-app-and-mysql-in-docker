package com.retail.orderservice.controller;

import com.retail.orderservice.dto.ProductDto;
import com.retail.orderservice.model.Product;
import com.retail.orderservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(value="page",defaultValue = "0") String page,@RequestParam(value="size",defaultValue = "2") String size){
        List<Product> allProducts = productService.getAllProducts(page, size).getContent();
        return new ResponseEntity<>(allProducts,null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") String productId){
        Optional<Product> product = productService.getProduct(productId);
        if(product.isPresent()){
            return new ResponseEntity<>(product.get().toString(),null, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Invalid Product Id",null, HttpStatus.OK);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productdto, @PathVariable("id") String productId){
        String responseString = productService.addProduct(productdto, productId);
        return  new ResponseEntity<>(responseString,
                null,
                HttpStatus.OK);
    }

}
