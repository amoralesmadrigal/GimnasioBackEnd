package com.project.microservicio.app.subscriptores.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservicio.app.subscriptores.models.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

	//@Query("select p from Persona p where p.username = :username and p.password = :password")
	public Optional<Persona> findByUsernameAndPassword(String username, String password);
	
	public Optional<Persona> findByUsername(String username);
	
	public Optional<Persona> findBySubscriptorId(String subscriptorId);
}
