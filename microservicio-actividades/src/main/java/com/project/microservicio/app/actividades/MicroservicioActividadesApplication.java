package com.project.microservicio.app.actividades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.project.microservicio.commons.models.entity"})
public class MicroservicioActividadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioActividadesApplication.class, args);
	}

}
