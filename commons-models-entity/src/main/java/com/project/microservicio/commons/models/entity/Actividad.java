package com.project.microservicio.commons.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "actividades")
public class Actividad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private Integer duracion;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private Date inicio;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private Date fin;
	
	@Column(name = "aforo_maximo")
	private Integer aforoMaximo;
	
	@Column(name = "aforo_actual")
	private Integer aforoActual = 0;
	
	private Boolean lleno;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@PrePersist
	public void prepersist() {
		createdAt = new Date();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Actividad)) {
			return false;
		}
		
		Actividad a = (Actividad) obj;
		
		return this.id != null && this.id.equals(a.getId());
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


	public Integer getDuracion() {
		return duracion;
	}


	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


	public Date getInicio() {
		return inicio;
	}


	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}


	public Date getFin() {
		return fin;
	}


	public void setFin(Date fin) {
		this.fin = fin;
	}


	public Integer getAforoMaximo() {
		return aforoMaximo;
	}


	public void setAforoMaximo(Integer aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}


	public Boolean getLleno() {
		return lleno;
	}


	public void setLleno(Boolean lleno) {
		this.lleno = lleno;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getAforoActual() {
		return aforoActual;
	}

	public void setAforoActual(Integer aforoActual) {
		this.aforoActual = aforoActual;
	}
}
