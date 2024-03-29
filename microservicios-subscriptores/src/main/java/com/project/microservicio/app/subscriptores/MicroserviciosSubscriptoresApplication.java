package com.project.microservicio.app.subscriptores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//@EnableCircuitBreaker
@EntityScan({"com.project.microservicio.commons.direcciones.models.entity",
		"com.project.microservicio.app.subscriptores.models.entity",
		"com.project.microservicio.commons.models.entity"})
public class MicroserviciosSubscriptoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosSubscriptoresApplication.class, args);
	}

}
