package com.project.microservicio.app.subscriptores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.microservicio.app.subscriptores.clients.DireccionFeignClient;
import com.project.microservicio.app.subscriptores.clients.DocumentoFeignClient;
import com.project.microservicio.app.subscriptores.models.entity.Empleado;
import com.project.microservicio.app.subscriptores.models.repository.EmpleadoRepository;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;
import com.project.microservicio.commons.services.CommonServiceImpl;

@Service
public class EmpleadoServiceImpl extends CommonServiceImpl<Empleado, EmpleadoRepository> implements EmpleadoService {

	@Autowired
	private DireccionFeignClient direccionFeignClient;
	
	@Autowired
	private DocumentoFeignClient documentoFeignClient;
	
	@Override
	public Direccion nuevo(Direccion entity) {
		return direccionFeignClient.nuevo(entity);
	}
	
	@Override
	public Direccion nuevoDireccion(Direccion entity) {
		return direccionFeignClient.nuevo(entity);
	}
	
	@Override
	public Direccion editarDireccion(Direccion direccion, Long id) {
		return direccionFeignClient.editarDireccion(direccion, id);
	}
	
	@Override
	public Direccion buscarDireccionById(Long id) {
		return direccionFeignClient.buscarDireccionById(id);
	}
	
	@Override
	public void eliminarDireccion(Long id) {
		direccionFeignClient.eliminar(id);
	}

	@Override
	public void eliminarFotoByEmpleadoId(Long id) {
		documentoFeignClient.eliminarFotoByEmpleadoId(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findByNombre(String term) {
		return repository.findByNombre(term);
	}

//	@Autowired
//	private EmpleadoRepository empleadoRepository;
//	
//	@Override
//	@Transactional(readOnly = true)
//	public Iterable<Empleado> findAll() {
//		return empleadoRepository.findAll();
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public Optional<Empleado> findById(Long id) {
//		return empleadoRepository.findById(id);
//	}
//
//	@Override
//	@Transactional
//	public Empleado guardar(Empleado empleado) {
//		return empleadoRepository.save(empleado);
//	}
//
//	@Override
//	@Transactional
//	public void eliminarEmpleadoById(Long id) {
//		empleadoRepository.deleteById(id);
//	}

}
