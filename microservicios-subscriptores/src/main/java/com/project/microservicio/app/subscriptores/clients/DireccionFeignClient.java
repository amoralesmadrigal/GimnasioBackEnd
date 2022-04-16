package com.project.microservicio.app.subscriptores.clients;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.microservicio.app.subscriptores.clients.fallbacks.DireccionFeignClientFallBack;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;

@FeignClient(name = "${feign.name.microservicio.direcciones}", fallback = DireccionFeignClientFallBack.class)
public interface DireccionFeignClient {
	
	@PostMapping("/direcciones-rest")
	public Direccion nuevo(@Valid @RequestBody Direccion entity);
	
	@PutMapping("/direcciones-rest/{id}")
	public Direccion editarDireccion(@RequestBody Direccion direccion, @PathVariable Long id);
	
	@DeleteMapping("/direcciones-rest/{id}")
	public void eliminar(@PathVariable Long id);
	
	@GetMapping("/direcciones-rest/buscar-direccion/{id}")
	public Direccion buscarDireccionById(@PathVariable Long id);
	
	
}
