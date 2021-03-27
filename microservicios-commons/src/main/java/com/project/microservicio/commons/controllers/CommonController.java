package com.project.microservicio.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservicio.commons.services.CommonService;

@RestController
public class CommonController <E, S extends CommonService<E>> {
	
	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
	}
	

	@GetMapping("/pagina")
	//desde json utiliza dos parametros page y size
	//page para cuantas paginas
	//size cuantos elementos por pagina
	public ResponseEntity<?> listar(Pageable pageable){
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> mostrar(@PathVariable Long id){
		Optional<E> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(o.get());
		}
	}
	
	
	@PostMapping
	//public ResponseEntity<?> crear(@RequestBody E entity){
	//para validar se agrega la anotacion @valid y como segundo argumento despues de entity BindingResult; importante no antes de entity
	public ResponseEntity<?> nuevo(@Valid @RequestBody E entity, BindingResult result){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		E entityDb = service.guardar(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody E entity, BindingResult result){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		E entityDb = service.guardar(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		Optional<E> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			service.eliminarById(id);
			return ResponseEntity.noContent().build();
		}
		
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
}
