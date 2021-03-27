package com.project.microservicio.app.documentos.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.microservicio.app.documentos.models.entity.Foto;
import com.project.microservicio.app.documentos.services.FotoService;

import feign.Headers;

@RestController
@RequestMapping("documentos-rest")
public class FotoController {

	@Autowired
	private FotoService service;
	
	@PostMapping(path="/foto/{subscriptorId}", consumes = {"multipart/form-data"})
	@Headers("Content-Type: multipart/form-data")
	public ResponseEntity<?> guardar(@PathVariable Long subscriptorId,  @RequestPart MultipartFile archivo) throws IOException{
		
		if(archivo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarFoto(subscriptorId, null));
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarFoto(subscriptorId, archivo.getBytes()));
		}

	}
	
	
	
	@GetMapping("/foto/{id}")
	public ResponseEntity<?> obtenerFoto(@PathVariable String id){
		
		Foto foto = service.obtenerFotoById(id);
		
		if(foto == null) {
			ResponseEntity.notFound().build();
		}
		
		ByteArrayResource byteArrayResource = new ByteArrayResource(foto.getBinaryData().getData());
		 
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArrayResource);
	}
	
	@GetMapping("/foto/subscriptor/{id}")
	public ResponseEntity<?> obtenerFotoBySubscriptorId(@PathVariable Long id){
		
		Foto foto = service.findBySubscriptorId(id);
		
		if(foto == null) {
			return ResponseEntity.notFound().build();
		}
		
		if(foto.getBinaryData() == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		ByteArrayResource byteArrayResource = new ByteArrayResource(foto.getBinaryData().getData());
		 
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArrayResource);
	}
	
	@DeleteMapping("/foto/{id}")
	public ResponseEntity<?> eliminarFoto(@PathVariable String id){
		
		Foto foto = service.findBySubscriptorId(Long.parseLong(id));
		if(foto == null) {
			return ResponseEntity.noContent().build();
		}else {
			service.eliminarFotoById(id);
			return ResponseEntity.noContent().build();	
		}
	}
	
	@DeleteMapping("/foto/subscriptor/{id}")
	public ResponseEntity<?> eliminarFotoBySubscriptorId(@PathVariable Long id){
		service.eliminarFotoBySubscriptorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/foto/empleado/{id}")
	public ResponseEntity<?> eliminarFotoByEmpleadoId(@PathVariable Long id){
		service.eliminarFotoByEmpleadoId(id);
		return ResponseEntity.noContent().build();
	}
	
}
