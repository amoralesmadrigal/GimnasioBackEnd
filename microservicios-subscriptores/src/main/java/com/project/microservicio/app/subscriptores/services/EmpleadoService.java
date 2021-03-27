package com.project.microservicio.app.subscriptores.services;

import java.util.List;

import com.project.microservicio.app.subscriptores.models.entity.Empleado;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.services.CommonService;

public interface EmpleadoService extends CommonService<Empleado> {

	public List<Empleado> findByNombre(String term);
	
	public Direccion nuevo(Direccion entity);
	
	public Direccion nuevoDireccion(Direccion entity);
	
	public Direccion editarDireccion(Direccion direccion, Long id);
	
	public Direccion buscarDireccionById(Long id);
	
	public void eliminarDireccion(Long id);
	
	public void eliminarFotoByEmpleadoId(Long id);	
}
