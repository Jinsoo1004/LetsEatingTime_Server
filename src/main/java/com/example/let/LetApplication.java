package com.example.let;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class LetApplication {

	public static void main(String[] args) {
		System.out.println("Server start!");
		SpringApplication.run(LetApplication.class, args);
	}

}
