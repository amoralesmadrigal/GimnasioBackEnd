package com.project.microservicio.app.actividades.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.microservicio.app.actividades.repository.ActividadRepository;
import com.project.microservicio.commons.models.entity.Actividad;
import com.project.microservicio.commons.services.CommonServiceImpl;

@Service
public class ActividadServiceImpl extends CommonServiceImpl<Actividad, ActividadRepository> implements ActividadService {

	@Override
	@Transactional(readOnly = true)
	public List<Actividad> findByNombre(String nombre) {
		return this.repository.findByNombre(nombre);
	}

	
}
