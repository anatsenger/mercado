package com.example.mercado;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(info = @Info(title = "Sample api", version = "1.0", description = "Sample API"))
@EnableCaching
@EnableRabbit
@SpringBootApplication
public class MercadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadoApplication.class, args);
    }

}
