package com.project.microservicio.app.subscriptores.services;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.project.microservicio.app.subscriptores.models.entity.Subscriptor;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.models.entity.Actividad;
import com.project.microservicio.commons.services.CommonService;

public interface SubscriptorService extends CommonService<Subscriptor> {

	public List<Subscriptor> findByNombre(String term);
	
	//microservicio direcciones
	public Direccion nuevoDireccion(Direccion entity);
	public Direccion editarDireccion(Direccion direccion, Long id);
	public void eliminarDireccion(Long id);
	public Direccion buscarDireccionById(Long id);

	//microservicio actividades
	public Actividad nuevo(Actividad entity);
	public Actividad editarActividad(Actividad actividad, Long id);
	public List<Actividad> nuevas(List<Actividad> actividad);
	public Actividad mostrar(Long id);
	public List<Actividad> listar();

	//Microservicio Documentos
	public String nuevoConFoto(Long subscriptorId, MultipartFile archivo);
	public ByteArrayResource obtenerFotoBySubscriptorId(Long id);
	public void eliminarFotoBySubscriptorId(Long id);
	public String editarFoto(Long subscriptorId, MultipartFile archivo);
	

}
