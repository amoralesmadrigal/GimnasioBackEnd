package com.project.microservicio.app.gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

	
	@RequestMapping("/direcciones-fallback")
	public Mono<String> direccionesServiceFallBack(){
		return Mono.just("fallback");
	}
	
	@RequestMapping("/actividades-fallback")
	public Mono<String> actividadesServiceFallBack(){
		return Mono.just("fallback");
	}
	
	@RequestMapping("/documentos-fallback")
	public Mono<String> documentosServiceFallBack(){
		return Mono.just("fallback");
	}
	
	
}
