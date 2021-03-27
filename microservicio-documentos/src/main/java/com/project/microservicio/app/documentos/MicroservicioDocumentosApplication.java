package com.project.microservicio.app.documentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroservicioDocumentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioDocumentosApplication.class, args);
	}

}
