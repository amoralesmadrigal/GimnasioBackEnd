package com.project.microservicio.app.subscriptores.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.project.microservicio.app.subscriptores.models.entity.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long> {

	@Query("select a from Empleado a where upper(a.nombre) like upper(concat('%',:nombre,'%'))")
	public List<Empleado> findByNombre(@Param("nombre")String nombre);
}
