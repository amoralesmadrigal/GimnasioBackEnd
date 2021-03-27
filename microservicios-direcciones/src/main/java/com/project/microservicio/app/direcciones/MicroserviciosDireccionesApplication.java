package com.project.microservicio.app.direcciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(value = {"com.project.microservicio.commons.direcciones.models.entity"})
public class MicroserviciosDireccionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosDireccionesApplication.class, args);
	}

}
