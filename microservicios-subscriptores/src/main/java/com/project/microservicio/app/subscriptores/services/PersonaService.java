package com.project.microservicio.app.subscriptores.services;

import java.util.Optional;

import com.project.microservicio.app.subscriptores.models.entity.Persona;

public interface PersonaService {

	public Optional<Persona> findById(Long id);
	
	public Optional<Persona> findByUsernameAndPassword(String username, String password);
	
	public Optional<Persona> findByUsername(String username);
	
	public Persona guardar(Persona persona);
	
	public void eliminarById(Long id);
}
