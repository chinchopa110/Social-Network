package com.example.webapp1;

import com.example.webapp1.Users.Service.Generators.IdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApp1Application {

	public static void main(String[] args) {
		SpringApplication.run(WebApp1Application.class, args);
	}

	@Bean
	public IdGenerator idGenerator() {
		return new IdGenerator();
	}
}
