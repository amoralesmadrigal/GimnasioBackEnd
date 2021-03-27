package com.project.microservicio.app.documentos.services;

import com.project.microservicio.app.documentos.models.entity.Foto;

public interface FotoService {

	public String guardarFoto(Long subscriptorId,  byte[] data);
	
	public Foto obtenerFotoById(String id);
	
	public Foto findBySubscriptorId(Long subscriptorId);
	
	public void eliminarFotoById(String id);
	
	public void eliminarFotoBySubscriptorId(Long subscriptorId);
	
	public void eliminarFotoByEmpleadoId(Long empleadoId);
	
}
