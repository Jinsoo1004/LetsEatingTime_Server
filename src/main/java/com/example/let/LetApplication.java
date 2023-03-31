package com.example.let;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.yaml.snakeyaml.Yaml;

@SpringBootApplication
@PropertySource(value = "classpath:/database.properties")
public class LetApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetApplication.class, args);
	}

}
