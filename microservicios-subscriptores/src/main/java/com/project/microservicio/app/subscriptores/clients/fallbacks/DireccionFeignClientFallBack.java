package com.project.microservicio.app.subscriptores.clients.fallbacks;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.project.microservicio.app.subscriptores.clients.DireccionFeignClient;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;

@Component
public class DireccionFeignClientFallBack implements DireccionFeignClient {

	@Override
	public Direccion nuevo(@Valid Direccion entity) {
		return null;
	}

	@Override
	public Direccion editarDireccion(Direccion direccion, Long id) {
		return null;
	}

	@Override
	public void eliminar(Long id) {
		
	}

	@Override
	public Direccion buscarDireccionById(Long id) {
		return null;
	}

}
