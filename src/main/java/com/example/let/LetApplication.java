package com.example.let;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
@PropertySource(value = "classpath:/database.properties")
public class LetApplication {

	public static void main(String[] args) {
		System.out.println("Server start!");
		SpringApplication.run(LetApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return route(GET("/api/swagger"), req ->
				ServerResponse.temporaryRedirect(URI.create("swagger-ui.html")).build());
	}
}
