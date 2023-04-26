package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.repository")
@SpringBootApplication
public class ProductDataManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductDataManagementSystemApplication.class, args);
	}

}
