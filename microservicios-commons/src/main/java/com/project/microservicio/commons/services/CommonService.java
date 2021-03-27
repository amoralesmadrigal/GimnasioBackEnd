package com.project.microservicio.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E> {

public Iterable<E> findAll();
	
	//metodo para pagina y ordenar
	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(Long id);
	
	public E guardar(E entity);
	
	public void eliminarById(Long id);
	
	
}
