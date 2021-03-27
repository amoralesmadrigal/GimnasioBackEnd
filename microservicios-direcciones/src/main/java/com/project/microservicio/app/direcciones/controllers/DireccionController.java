package com.project.microservicio.app.direcciones.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservicio.app.direcciones.services.DireccionService;
import com.project.microservicio.commons.controllers.CommonController;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;

@RestController
@RequestMapping("direcciones-rest")
public class DireccionController extends CommonController<Direccion, DireccionService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> editarDireccion(@RequestBody Direccion direccion, @PathVariable Long id){
		Optional<Direccion> direccionOpt = service.findById(id);
		
		if(!direccionOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Direccion direccionDB = direccionOpt.get();
		direccionDB.setCalle(direccion.getCalle());
		direccionDB.setCiudad(direccion.getCiudad());
		direccionDB.setCodigoPostal(direccion.getCodigoPostal());
		direccionDB.setNumero(direccion.getNumero());
		direccionDB.setPiso(direccion.getPiso());
		direccionDB.setPuerta(direccion.getPuerta());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(direccionDB));
		
	}
	
	@GetMapping("/buscar-direccion/{id}")
	public ResponseEntity<?> buscarDireccionById(@PathVariable Long id){
		Optional<Direccion> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(o.get());
		}
	}
	
}
