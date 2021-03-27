package com.project.microservicio.app.documentos.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.project.microservicio.app.documentos.models.entity.Foto;

public interface FotoRepository extends MongoRepository<Foto, String> {

	@Query("{'subscriptorId' : ?0}")
	public Optional<Foto> findBySubscriptorId(Long subscriptorId);
	
	@Query("{'empleadoId' : ?0}")
	public Optional<Foto> findByEmpleadoId(Long empleadoId);
	
}
