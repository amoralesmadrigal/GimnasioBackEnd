package com.project.microservicio.app.subscriptores.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subscripciones_direcciones")
public class SubscriptorDireccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "direccion_id", unique = true)
	private Long direccionId;
	
	@JsonIgnoreProperties(value = {"subscriptoresDirecciones"})
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriptor_id")
	private Subscriptor subscriptor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Long direccionId) {
		this.direccionId = direccionId;
	}

	public Subscriptor getSubscriptor() {
		return subscriptor;
	}

	public void setSubscriptor(Subscriptor subscriptor) {
		this.subscriptor = subscriptor;
	}

	
}
