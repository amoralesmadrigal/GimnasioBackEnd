package com.project.microservicio.app.subscriptores.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.microservicio.app.subscriptores.models.entity.Persona;
import com.project.microservicio.app.subscriptores.models.entity.Subscriptor;
import com.project.microservicio.app.subscriptores.models.entity.SubscriptorActividad;
import com.project.microservicio.app.subscriptores.models.entity.SubscriptorDireccion;
import com.project.microservicio.app.subscriptores.services.PersonaService;
import com.project.microservicio.app.subscriptores.services.SubscriptorService;
import com.project.microservicio.commons.controllers.CommonController;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.models.entity.Actividad;

import feign.FeignException;
import feign.Headers;

@RestController
@RequestMapping(path = "subscriptores-rest")
public class SubscriptorController extends CommonController<Subscriptor, SubscriptorService> {

	@Autowired
	private PersonaService personaService; 
	
	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<>();
		response.put("balanceadorTest", balanceadorTest);
		response.put("subscriptores", service.findAll());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(path = "/crear-con-foto", consumes = { "multipart/form-data" })
	@Headers("Content-Type: multipart/form-data")
	public ResponseEntity<?> nuevoConFoto(@Valid Subscriptor subscritor, BindingResult result,
			@RequestPart MultipartFile archivo) throws IOException {
		String fotoId = null;
		Subscriptor subscriptorDB = null;

		if (result.hasErrors()) {
			return this.validar(result);
		}
		subscriptorDB = service.guardar(subscritor);

		
		// microservicio documentos
		try {
			fotoId = service.nuevoConFoto(subscriptorDB.getId(), archivo);
			subscriptorDB.setFotoId(fotoId);
		} catch (Exception e) {
			service.eliminarById(subscriptorDB.getId());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(subscriptorDB));
	}

	@PutMapping(path = "/editar-con-foto/{subscriptorId}", consumes = { "multipart/form-data" })
	@Headers("Content-Type: multipart/form-data")
	public ResponseEntity<?> editarConFoto(@PathVariable Long subscriptorId, @RequestPart MultipartFile archivo)
			throws IOException {

		Optional<Subscriptor> subscriptorOpt = service.findById(subscriptorId);

		if (subscriptorOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.nuevoConFoto(subscriptorId, archivo));

	}

	@PostMapping("/subscriptor-direccion/{personaId}")
	public ResponseEntity<?> nuevoConDireccion(@Valid @RequestBody Subscriptor entity, @PathVariable Long personaId, BindingResult result) {
		Direccion direccion = null;

		Optional<Persona> personaOpt = personaService.findById(personaId);
		
		if (personaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Persona personaDB = personaOpt.get();
		
		
		if (result.hasErrors()) {
			return this.validar(result);
		}

		try {
			// MicroServicio de direcciones
			if(entity.getDireccion() != null) {
				direccion = service.nuevoDireccion(entity.getDireccion());	
			}
			

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		SubscriptorDireccion sd = new SubscriptorDireccion();
		sd.setDireccionId(direccion.getId());
		sd.setSubscriptor(entity);
		entity.setSubscriptoresDirecciones(sd);
		entity.setPersonaId(personaDB);
		Subscriptor entityDb = service.guardar(entity);
		
		
		personaDB.setSubscriptorId(entityDb);
		//actualizar persona con id
		//personaDB.setSubscriptorId(entity);
		Persona guardar = personaService.guardar(personaDB);
		
		//aqui seria mejor guardar personas y no subscriptores
		return ResponseEntity.status(HttpStatus.CREATED).body(guardar.getSubscriptorId());
	}

	@PostMapping("/subscriptor-actividad/{idSubscriptor}/{idActividad}")
	public ResponseEntity<?> asignarActividad(@PathVariable Long idSubscriptor, @PathVariable Long idActividad) {
		Actividad actividadDB = null;
		Optional<Subscriptor> subscriptorOpt = service.findById(idSubscriptor);

		if (subscriptorOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		try {
			// MicroServicio de actividades
			actividadDB = service.mostrar(idActividad);

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		Subscriptor subscriptorDB = subscriptorOpt.get();
		SubscriptorActividad subscriptorActividad = new SubscriptorActividad();
		subscriptorActividad.setActividadId(idActividad);
		subscriptorActividad.setSubscriptor(subscriptorDB);

		subscriptorDB.addSubscriptorActividad(subscriptorActividad);
		subscriptorDB.addActividad(actividadDB);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(subscriptorDB));
	}

	@PutMapping("/subscriptor-actividad/{idSubscriptor}")
	public ResponseEntity<?> editarActividadesSubscriptor(@RequestBody List<Long> actividades,
			@PathVariable Long idSubscriptor) {

		// Buscar primero el cliente
		Optional<Subscriptor> subscriptorOpt = service.findById(idSubscriptor);

		if (subscriptorOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Subscriptor subscriptorDB = subscriptorOpt.get();

		for (Iterator<SubscriptorActividad> iterator = subscriptorDB.getSubscriptorActividad().iterator(); iterator
				.hasNext();) {
			SubscriptorActividad integer = iterator.next();
			if (actividades.contains(integer.getActividadId())) {
				iterator.remove();
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(subscriptorDB));
	}

	@PostMapping("/subscriptor-actividad/{idSubscriptor}")
	public ResponseEntity<?> asignarActividades(@RequestBody List<Long> actividades, @PathVariable Long idSubscriptor) {

		Map<Long, Subscriptor> subsGuardados = new HashMap<>();

		// Buscar primero el cliente
		Optional<Subscriptor> subscriptorOpt = service.findById(idSubscriptor);

		if (subscriptorOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Subscriptor subscriptorDB = subscriptorOpt.get();
		try {

			actividades.forEach((Long id) -> {

				SubscriptorActividad subscriptorActividad = new SubscriptorActividad();
				subscriptorActividad.setActividadId(id);
				subscriptorActividad.setSubscriptor(subscriptorDB);

				subscriptorDB.addSubscriptorActividad(subscriptorActividad);
				// subscriptorDB.addActividad(actividadDB);

				Subscriptor subscriptorGuardado = service.guardar(subscriptorDB);
				subsGuardados.putIfAbsent(idSubscriptor, subscriptorGuardado);

			});

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(subsGuardados);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editarSubscriptor(@RequestBody Subscriptor subscriptor, @PathVariable Long id) {
		Optional<Subscriptor> subscriptorOp = service.findById(id);

		if (subscriptorOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		//podria agregar validaciones, si viene lleno modificar el objeto 
		Subscriptor subscriptorDB = subscriptorOp.get();
		subscriptorDB.setDocumentacion(subscriptor.getDocumentacion());
		subscriptorDB.setEmail(subscriptor.getEmail());
		subscriptorDB.setFechaNacimiento(subscriptor.getFechaNacimiento());
		subscriptorDB.setNombre(subscriptor.getNombre());
		subscriptorDB.setNumeroTelefono(subscriptor.getNumeroTelefono());
		subscriptorDB.setPrimerApellido(subscriptor.getPrimerApellido());
		subscriptorDB.setSegundoApellido(subscriptor.getSegundoApellido());
//		subscriptorDB.setUserName(subscriptor.getUserName());
//		subscriptorDB.setPassword(subscriptor.getPassword());
//		subscriptorDB.setConfirmPassword(subscriptor.getConfirmPassword());
		subscriptorDB.setStatus(subscriptor.getStatus());
		
		Direccion direccion = subscriptor.getDireccion();
		if(direccion == null) {
			direccion = new Direccion();
		}
		direccion.setSubscriptorId(id);
		
		Direccion direccionDB = null;
		boolean tieneDireccion = false;
		try {
			// MicroServicio de direcciones
			// Si no tiene se agrega direccion
			if (subscriptorDB.getSubscriptoresDirecciones() == null) {
				direccionDB = service.nuevoDireccion(direccion);
				SubscriptorDireccion sd = new SubscriptorDireccion();
				sd.setDireccionId(direccionDB.getId());
				sd.setSubscriptor(subscriptorDB);
				subscriptorDB.setSubscriptoresDirecciones(sd);
			} else {
				tieneDireccion = true;
				direccionDB = service.editarDireccion(direccion,
						subscriptorDB.getSubscriptoresDirecciones().getDireccionId());
			}
			
			subscriptorDB.setDireccion(direccionDB);

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		Subscriptor regreso = null;
		try {
			regreso = service.guardar(subscriptorDB);
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

	@DeleteMapping("/all/{id}")
	public ResponseEntity<?> eliminarTodo(@PathVariable Long id) {
		
		Optional<Subscriptor> subscriptorOp = service.findById(id);
		
		if (subscriptorOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Subscriptor subscriptorDB = subscriptorOp.get();
		
		Optional<Persona> personaOp = personaService.findById(subscriptorDB.getPersonaId().getId());
		
		if (personaOp.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		// microservicio eliminar direccion
		SubscriptorDireccion subscriptoresDirecciones = subscriptorDB.getSubscriptoresDirecciones();

		try {
			if (subscriptoresDirecciones != null) {
				service.eliminarDireccion(subscriptoresDirecciones.getDireccionId());
			}
			
			// microservicio eliminar foto
			if(subscriptorDB.getFotoId() != null) {
				service.eliminarFotoBySubscriptorId(id);	
			}
			

		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		personaService.eliminarById(subscriptorDB.getPersonaId().getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> mostrar(@PathVariable Long id){
		Optional<Subscriptor> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Subscriptor subscriptorBD = o.get();
		if(subscriptorBD.getSubscriptoresDirecciones() != null) {
			Direccion buscarDireccionById = service.buscarDireccionById(subscriptorBD.getSubscriptoresDirecciones().getDireccionId());	
			subscriptorBD.setDireccion(buscarDireccionById);
		}
		
		return ResponseEntity.ok().body(subscriptorBD);
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok().body(service.findByNombre(term));
	}

}
