package com.example.webapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApp1Application {

	public static void main(String[] args) {
		SpringApplication.run(WebApp1Application.class, args);
	}
}
