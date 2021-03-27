package com.project.microservicio.app.actividades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.project.microservicio.commons.models.entity.Actividad;

public interface ActividadRepository extends PagingAndSortingRepository<Actividad, Long> {
//select a from Alumno a where upper(a.nombre) like upper(concat('%',?1,'%')) or upper(a.apellido) like upper(concat('%',?1,'%'))
	@Query("select a from Actividad a where upper(a.nombre) like upper(concat('%',:nombre,'%'))")
	public List<Actividad> findByNombre(@Param("nombre")String nombre);
}
