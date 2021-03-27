package com.project.microservicio.app.subscriptores.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservicio.app.subscriptores.models.entity.Empleado;
import com.project.microservicio.app.subscriptores.models.entity.EmpleadoDireccion;
import com.project.microservicio.app.subscriptores.models.entity.Persona;
import com.project.microservicio.app.subscriptores.services.EmpleadoService;
import com.project.microservicio.app.subscriptores.services.PersonaService;
import com.project.microservicio.commons.controllers.CommonController;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;

import feign.FeignException;

@RestController
@RequestMapping(path = "empleados-rest")
public class EmpleadoController extends CommonController<Empleado, EmpleadoService> {

	@Autowired
	private PersonaService personaService; 
	
//	@Autowired
//	private EmpleadoService empleadoService;
	
//	@GetMapping
//	public ResponseEntity<?> listar(){
//		return ResponseEntity.ok().body(empleadoService.findAll());
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<?> mostrar(@PathVariable Long id){
//		Optional<Empleado> empleadoOp = empleadoService.findById(id);
//		
//		if(empleadoOp.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		return ResponseEntity.ok().body(empleadoOp.get());
//	}
//	
//	@PostMapping 
//	public ResponseEntity<?> nuevo(@RequestBody Empleado empleado){
//		Empleado empleadoDB = service.guardar(empleado);
//		return ResponseEntity.status(HttpStatus.CREATED).body(empleadoDB);
//	}
	
	@PostMapping("/empleado-direccion/{personaId}")
	public ResponseEntity<?> nuevoConDireccion(@Valid @RequestBody Empleado entity, @PathVariable Long personaId, BindingResult result) {

		Direccion direccion = null;
		
		Optional<Persona> personaOpt = personaService.findById(personaId);
		
		if (personaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Persona personaDB = personaOpt.get();
		
		//Primero Validar el objecto
		if(result.hasErrors()) {
			return this.validar(result);
		}

		try {
			//MicroServicio de direcciones  
			direccion = service.nuevo(entity.getDireccion());
			
		}catch(FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		EmpleadoDireccion sd = new EmpleadoDireccion();
		sd.setDireccionId(direccion.getId());
		sd.setEmpleado(entity);
		entity.setEmpleadosDirecciones(sd);
		entity.setPersonaId(personaDB);
		
		Empleado entityDb = service.guardar(entity);
		
		personaDB.setEmpleadoId(entityDb);
		Persona guardar = personaService.guardar(personaDB);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(guardar.getEmpleadoId().getId());
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editarEmpleado(@RequestBody Empleado empleado, @PathVariable Long id){
		Optional<Empleado> empleadoOp = service.findById(id);
		
		if(empleadoOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Empleado empleadoDB = empleadoOp.get();
		empleadoDB.setDocumentacion(empleado.getDocumentacion());
		empleadoDB.setEmail(empleado.getEmail());
		empleadoDB.setFechaNacimiento(empleado.getFechaNacimiento());
		empleadoDB.setNombre(empleado.getNombre());
		empleadoDB.setNumeroTelefono(empleado.getNumeroTelefono());
		empleadoDB.setPrimerApellido(empleado.getPrimerApellido());
		empleadoDB.setSegundoApellido(empleado.getSegundoApellido());
		empleadoDB.setTipoEmpleado(empleado.getTipoEmpleado());

		Direccion direccion = empleado.getDireccion();
		if(direccion == null) {
			direccion = new Direccion();
		}
		direccion.setEmpleadoId(id);
		
		Direccion direccionDB = null;
		boolean tieneDireccion = false;
		try {
			// MicroServicio de direcciones
			// Si no tiene se agrega direccion
			if (empleadoDB.getEmpleadosDirecciones() == null) {
				direccionDB = service.nuevoDireccion(direccion);
				EmpleadoDireccion ed = new EmpleadoDireccion();
				ed.setDireccionId(direccionDB.getId());
				ed.setEmpleado(empleadoDB);
				empleadoDB.setEmpleadosDirecciones(ed);
			} else {
				tieneDireccion = true;
				direccionDB = service.editarDireccion(direccion,
						empleadoDB.getEmpleadosDirecciones().getDireccionId());
			}
			
			empleadoDB.setDireccion(direccionDB);

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		Empleado regreso = null;
		try {
			regreso = service.guardar(empleadoDB);
		} catch (Exception e) {
			// MicroServicio de direcciones
			// Si falla el guardar y es nueva la direccion entonces eliminar la direccion
			// nueva
			if (tieneDireccion == false) {
				service.eliminarDireccion(direccionDB.getId());
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(regreso);
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> mostrar(@PathVariable Long id){
		Optional<Empleado> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Empleado subscriptorBD = o.get();
		if(subscriptorBD.getEmpleadosDirecciones() != null) {
			Direccion buscarDireccionById = service.buscarDireccionById(subscriptorBD.getEmpleadosDirecciones().getDireccionId());	
			subscriptorBD.setDireccion(buscarDireccionById);
		}
		
		return ResponseEntity.ok().body(subscriptorBD);
	}
	
	@DeleteMapping("/all/{id}")
	public ResponseEntity<?> eliminarTodo(@PathVariable Long id) {
		
		Optional<Empleado> empleadoOp = service.findById(id);
		
		if (empleadoOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Empleado empleadoDB = empleadoOp.get();
		
		Optional<Persona> personaOp = personaService.findById(empleadoDB.getPersonaId().getId());
		
		if (personaOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		// microservicio eliminar direccion
		EmpleadoDireccion empleadosDirecciones = empleadoDB.getEmpleadosDirecciones();

		try {
			if (empleadosDirecciones != null) {
				service.eliminarDireccion(empleadosDirecciones.getDireccionId());
			}
			
			// microservicio eliminar foto
			if(empleadoDB.getFotoId() != null) {
				service.eliminarFotoByEmpleadoId(id);	
			}
			

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		personaService.eliminarById(empleadoDB.getPersonaId().getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok().body(service.findByNombre(term));
	}
	
	
}
