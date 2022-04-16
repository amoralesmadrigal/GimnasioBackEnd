package com.project.microservicio.app.subscriptores.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.microservicio.commons.models.entity.Actividad;

@FeignClient(name = "${feign.name.microservicio.actividades}")
public interface ActividadFeignClient {
	
	@PostMapping("/actividades-rest")
	public Actividad nuevo(@RequestBody Actividad entity);
	
	@PutMapping("/actividades-rest/{id}")
	public Actividad editarActividad(@RequestBody Actividad actividad, @PathVariable Long id);
	
	@PostMapping("/actividades-rest/nuevas")
	public List<Actividad> nuevas(@RequestBody List<Actividad> actividades);
	
	@GetMapping("/actividades-rest/{id}")
	public Actividad mostrar(@PathVariable Long id);

	@GetMapping("/actividades-rest")
	List<Actividad> listar();
	
}
