package com.project.microservicio.app.subscriptores.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservicio.app.subscriptores.models.entity.Persona;
import com.project.microservicio.app.subscriptores.models.entity.TipoEmpleado;
import com.project.microservicio.app.subscriptores.services.PersonaService;

@RestController
@RequestMapping(path = "personas-rest")
public class PersonaController {
	
	@Autowired
	private PersonaService service;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody Persona persona, BindingResult result){
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Persona> personaOpt = service.findByUsernameAndPassword(persona.getUsername(), persona.getPassword());
		
		if(personaOpt.isEmpty()) {
			Map<String, Object> errores = new HashMap<>();
			errores.put("datos", "Username o Password son incorrectos");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errores);
		}
		
		return ResponseEntity.accepted().body(personaOpt.get());
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody Persona persona, BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		if(StringUtils.isEmpty(persona.getConfirmPassword())) {
			errores.put("password", "El campo confirmPassword no puede estar vacio");
			return ResponseEntity.badRequest().body(errores);
		}
		
		if(!persona.getPassword().equals(persona.getConfirmPassword())) {
			errores.put("password", "El campo password y confirmPassword no son iguales");
			return ResponseEntity.badRequest().body(errores);
		}
		
		Optional<Persona> usernameOpt = service.findByUsername(persona.getUsername());
		
		if(!usernameOpt.isEmpty()) {
			errores.put("username", "El username ya existe");
			return ResponseEntity.badRequest().body(errores);
		}
		
		Persona personaDB = service.guardar(persona);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personaDB.getId());
	}
	
	@PutMapping("/update-password/{id}")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody Persona persona, @PathVariable Long id, BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		if(StringUtils.isEmpty(persona.getConfirmPassword())) {
			errores.put("password", "El campo confirmPassword no puede estar vacio");
			return ResponseEntity.badRequest().body(errores);
		}
		
		if(!persona.getPassword().equals(persona.getConfirmPassword())) {
			errores.put("password", "El campo password y confirmPassword no son iguales");
			return ResponseEntity.badRequest().body(errores);
		}
		
		Optional<Persona> personaOpt = service.findById(id);
		if(personaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Persona personaDB = personaOpt.get();
		personaDB.setPassword(persona.getPassword());
		personaDB.setConfirmPassword(persona.getConfirmPassword());
		
		Persona regreso = service.guardar(personaDB);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(regreso.getId());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRol(@PathVariable Long id){
		
		Optional<Persona> personaOpt = service.findById(id);
		if(personaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Persona personaDB = personaOpt.get();
		
		if(personaDB.getSubscriptorId() != null) {
			return ResponseEntity.accepted().body(TipoEmpleado.SUBSCRIPTOR.ordinal());
		}else {
			return ResponseEntity.accepted().body(personaDB.getEmpleadoId().getTipoEmpleado().ordinal());
		}
		
		
		
	}

}
