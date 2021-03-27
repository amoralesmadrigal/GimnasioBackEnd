package com.project.microservicio.app.subscriptores.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.microservicio.commons.direcciones.models.entity.Direccion;

@MappedSuperclass
public abstract class CommonData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre" , length = 20)
	@NotNull
	@NotBlank
	@Max(value = 20)
	private String nombre;
	
	@Column(name = "primer_apellido",  length = 20)
	@NotNull
	@NotBlank
	@Max(value = 20)
	private String primerApellido;
	
	@Column(name = "segundo_apellido", length = 20)
	@Max(value = 20)
	private String segundoApellido;
	
	@Column(name = "documentacion", length = 15)
	@NotNull
	@NotBlank
	@Max(value = 15)
	private String documentacion;
	
	@Column(name = "numero_telefono", length = 10)
	@Max(value = 10)
	private String numeroTelefono;
	
	@Column(name = "email", length = 30)
	@Email
	@Max(value = 30)
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "fecha_nacimiento", length = 30)
	@NotNull
	@NotBlank
	@Max(value = 30)
	private String fechaNacimiento;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "foto_id")
	private String fotoId;
	
	@Transient
	private Direccion direccion;
	
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getFotoId() {
		return fotoId;
	}

	public void setFotoId(String fotoId) {
		this.fotoId = fotoId;
	}


}
