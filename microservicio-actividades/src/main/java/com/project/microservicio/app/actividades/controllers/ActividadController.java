package com.project.microservicio.app.actividades.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservicio.app.actividades.services.ActividadService;
import com.project.microservicio.commons.controllers.CommonController;
import com.project.microservicio.commons.models.entity.Actividad;

@RestController
@RequestMapping("actividades-rest")
public class ActividadController extends CommonController<Actividad, ActividadService> {


	@PostMapping("/nuevas")
	public ResponseEntity<?> nuevas(@RequestBody List<Actividad> actividad){
		
		List<Actividad> regreso = new ArrayList<>();
		actividad.forEach((Actividad act) ->{
			Actividad actividadDB = service.guardar(act);
			regreso.add(actividadDB);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(regreso);
		
	}

	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok().body(service.findByNombre(term));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editarActividad(@RequestBody Actividad actividad, @PathVariable Long id){
		Optional<Actividad> direccionOpt = service.findById(id);
		
		if(!direccionOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Actividad actividadDB = direccionOpt.get();
		actividadDB.setAforoMaximo(actividad.getAforoMaximo());
		actividadDB.setAforoActual(actividad.getAforoActual());
		actividadDB.setDuracion(actividad.getDuracion());
		actividadDB.setFin(actividad.getFin());
		actividadDB.setInicio(actividad.getInicio());
		actividadDB.setLleno(actividad.getLleno());
		actividadDB.setNombre(actividad.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(actividadDB));
		
	}
	@Override
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Actividad> findAll = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
		findAll.sort(Comparator.comparingLong(Actividad::getId));
		return ResponseEntity.ok().body(findAll);
	}
	
}
