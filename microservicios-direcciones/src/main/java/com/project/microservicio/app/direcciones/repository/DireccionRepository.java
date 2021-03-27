package com.project.microservicio.app.direcciones.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.microservicio.commons.direcciones.models.entity.Direccion;

public interface DireccionRepository extends PagingAndSortingRepository<Direccion, Long>{

}
