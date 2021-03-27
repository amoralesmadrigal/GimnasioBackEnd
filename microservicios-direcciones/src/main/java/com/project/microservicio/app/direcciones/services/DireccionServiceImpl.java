package com.project.microservicio.app.direcciones.services;

import org.springframework.stereotype.Service;

import com.project.microservicio.app.direcciones.repository.DireccionRepository;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.services.CommonServiceImpl;

@Service
public class DireccionServiceImpl extends CommonServiceImpl<Direccion, DireccionRepository> implements DireccionService {

	

}
