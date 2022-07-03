package com.retail.orderservice;

import com.retail.orderservice.model.Product;
import com.retail.orderservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class OrderServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}


	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

	@Autowired
	ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		List<Product> product= Arrays.asList(new Product("cc2022","Chair",2,new BigDecimal(250)),
		new Product("bb2022","Books",1,new BigDecimal(150)),
				new Product("dd2022","Dell PC",1,new BigDecimal(2500)),

				new Product("hh2022","Television",12,new BigDecimal(1050)));
		productRepository.saveAll(product);
	}
}
