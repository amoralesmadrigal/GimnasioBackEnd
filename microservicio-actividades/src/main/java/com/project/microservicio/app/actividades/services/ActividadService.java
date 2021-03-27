package com.project.microservicio.app.actividades.services;

import java.util.List;

import com.project.microservicio.commons.models.entity.Actividad;
import com.project.microservicio.commons.services.CommonService;

public interface ActividadService extends CommonService<Actividad> {
	
	public List<Actividad> findByNombre(String nombre);

}
