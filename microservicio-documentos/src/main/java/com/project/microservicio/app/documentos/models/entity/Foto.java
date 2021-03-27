package com.project.microservicio.app.documentos.models.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fotos")
public class Foto {

	@Id
	private String id;
	
	private Long subscriptorId;
	
	private Long empleadoId;
	
	private Binary binaryData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public Binary getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(Binary imagen) {
		this.binaryData = imagen;
	}

	public Long getSubscriptorId() {
		return subscriptorId;
	}

	public void setSubscriptorId(Long subscriptorId) {
		this.subscriptorId = subscriptorId;
	}

	public Long getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}

}
