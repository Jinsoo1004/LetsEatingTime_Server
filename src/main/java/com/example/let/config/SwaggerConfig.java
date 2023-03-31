package com.example.let.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "classpath:/document.properties")
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Team.ALT, REST API List")
                .version("0.0.1")
                .description("LetsEatingTime 서비스 종합 api 명세서.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}