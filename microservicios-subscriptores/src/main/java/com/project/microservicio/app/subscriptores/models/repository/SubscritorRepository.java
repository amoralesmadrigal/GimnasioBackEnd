package com.project.microservicio.app.subscriptores.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.project.microservicio.app.subscriptores.models.entity.Subscriptor;

public interface SubscritorRepository extends PagingAndSortingRepository<Subscriptor, Long> {

	@Query("select a from Subscriptor a where upper(a.nombre) like upper(concat('%',:nombre,'%'))")
	public List<Subscriptor> findByNombre(@Param("nombre")String nombre);
}
