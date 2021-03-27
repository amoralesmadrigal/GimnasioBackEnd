package com.project.microservicio.app.documentos.services;

import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.microservicio.app.documentos.models.entity.Foto;
import com.project.microservicio.app.documentos.repository.FotoRepository;

@Service
public class FotoServiceImpl implements FotoService {

	@Autowired FotoRepository repository; 
	
	@Override
	public String guardarFoto(Long subscriptorId, byte[] data) {
		
		Foto fotoDB = null;
		Foto insert = null;
		Optional<Foto> optional = repository.findBySubscriptorId(subscriptorId);
		
		if(!optional.isEmpty()) {
			fotoDB = optional.get();
			
			if(data != null && data.length > 0) {
				fotoDB.setBinaryData(new Binary(BsonBinarySubType.BINARY, data));	
			}else {
				fotoDB.setBinaryData(null);
			}
			insert = repository.save(fotoDB);
		}else {
			Foto foto = new Foto();
			foto.setSubscriptorId(subscriptorId);
			
			if(data != null && data.length > 0) {
				foto.setBinaryData(new Binary(BsonBinarySubType.BINARY, data));	
			}
			insert = repository.save(foto);
		}
		
		return insert.getId(); 
	}
	
	

	@Override
	public Foto obtenerFotoById(String id) {
		Foto regreso = null;
		Optional<Foto> optional = repository.findById(id);
		
		if(!optional.isEmpty()) {
			regreso = optional.get();
		}
		
		return regreso;
	}

	@Override
	public Foto findBySubscriptorId(Long subscriptorId) {
		Foto regreso = null;
		Optional<Foto> optional = repository.findBySubscriptorId(subscriptorId);
		
		if(!optional.isEmpty()) {
			regreso = optional.get();
		}
		
		return regreso;
	}

	@Override
	public void eliminarFotoById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void eliminarFotoBySubscriptorId(Long subscriptorId) {
		Foto regreso = null;
		Optional<Foto> optional = repository.findBySubscriptorId(subscriptorId);
		
		if(!optional.isEmpty()) {
			regreso = optional.get();
			repository.delete(regreso);
		}
		
	}
	
	@Override
	public void eliminarFotoByEmpleadoId(Long empleadoId) {
		Foto regreso = null;
		Optional<Foto> optional = repository.findByEmpleadoId(empleadoId);
		
		if(!optional.isEmpty()) {
			regreso = optional.get();
			repository.delete(regreso);
		}
		
	}

	

}
