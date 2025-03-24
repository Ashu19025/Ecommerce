package com.example.Ecommcerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Ecommcerce.models")
@EnableJpaRepositories(basePackages = "com.example.Ecommcerce.repository")
public class EcommcerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommcerceApplication.class, args);
	}

}
