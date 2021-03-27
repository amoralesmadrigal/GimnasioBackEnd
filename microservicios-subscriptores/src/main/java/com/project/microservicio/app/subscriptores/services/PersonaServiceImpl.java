package com.project.microservicio.app.subscriptores.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.microservicio.app.subscriptores.models.entity.Persona;
import com.project.microservicio.app.subscriptores.models.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository repository;
	
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}
	
	@Override
	@Transactional
	public Persona guardar(Persona persona) {
		return repository.save(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	@Transactional
	public void eliminarById(Long id) {
		repository.deleteById(id);
		
	}

}
