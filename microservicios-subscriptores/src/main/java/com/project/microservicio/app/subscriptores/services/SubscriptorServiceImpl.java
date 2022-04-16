package com.project.microservicio.app.subscriptores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.microservicio.app.subscriptores.clients.ActividadFeignClient;
import com.project.microservicio.app.subscriptores.clients.DireccionFeignClient;
import com.project.microservicio.app.subscriptores.clients.DocumentoFeignClient;
import com.project.microservicio.app.subscriptores.models.entity.Subscriptor;
import com.project.microservicio.app.subscriptores.models.repository.SubscritorRepository;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.models.entity.Actividad;
import com.project.microservicio.commons.services.CommonServiceImpl;

@Service
public class SubscriptorServiceImpl extends CommonServiceImpl<Subscriptor, SubscritorRepository> implements SubscriptorService{

	@Autowired
	private DireccionFeignClient direccionFeignClient;
	
	@Autowired
	private ActividadFeignClient actividadFeignClient;
	
	@Autowired
	private DocumentoFeignClient documentoFeignClient;
	
	///////////////Microservicio Direcciones
	
	@Override
	public Direccion nuevoDireccion(Direccion entity) {
		return direccionFeignClient.nuevo(entity);
	}
	
	@Override
	public Direccion editarDireccion(Direccion direccion, Long id) {
		return direccionFeignClient.editarDireccion(direccion, id);
	}
	
	@Override
	public void eliminarDireccion(Long id) {
		direccionFeignClient.eliminar(id);
	}

	///////////////Microservicio Actividades
	@Override
	public Actividad nuevo(Actividad entity) {
		return actividadFeignClient.nuevo(entity);
	}

	@Override
	public Actividad editarActividad(Actividad actividad, Long id) {
		return actividadFeignClient.editarActividad(actividad, id);
	}
	
	@Override
	public List<Actividad> nuevas(List<Actividad> actividades) {
		return actividadFeignClient.nuevas(actividades);
	}

	@Override
	public Actividad mostrar(Long id) {
		return actividadFeignClient.mostrar(id);
	}

	@Override
	public List<Actividad> listar() {
		return actividadFeignClient.listar();
	}

	///////////////Microservicio Documentos
	@Override
	public String nuevoConFoto(Long subscriptorId, MultipartFile archivo) {
		return documentoFeignClient.guardar(subscriptorId, archivo);
	}

	@Override
	public ByteArrayResource obtenerFotoBySubscriptorId(Long id) {
		return documentoFeignClient.obtenerFotoBySubscriptorId(id);
	}

	@Override
	public void eliminarFotoBySubscriptorId(Long id) {
		documentoFeignClient.eliminarFotoBySubscriptorId(id);
	}

	@Override
	public String editarFoto(Long subscriptorId, MultipartFile archivo) {
		return documentoFeignClient.editar(subscriptorId, archivo);
	}

	@Override
	public Direccion buscarDireccionById(Long id) {
		return direccionFeignClient.buscarDireccionById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subscriptor> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	

	

	
}
