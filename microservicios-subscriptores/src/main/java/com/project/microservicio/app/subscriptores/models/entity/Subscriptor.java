package com.project.microservicio.app.subscriptores.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.microservicio.commons.models.entity.Actividad;

@Entity
@Table(name = "subscriptores")
public class Subscriptor extends CommonData{

	public Subscriptor() {
		this.actividades = new ArrayList<>();
		this.subscriptorActividad = new ArrayList<>();
	}
	
	private Boolean status;
	
	@Column(name = "tipo_inscripcion", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TipoInscripcion tipoInscripcion;
	
	@JsonIgnoreProperties(value = {"subscriptor"}, allowSetters = true)
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "subscriptor", cascade = CascadeType.ALL, orphanRemoval = true)
	private SubscriptorDireccion subscriptoresDirecciones;
	
	@Transient
	private List<Actividad> actividades;
	
	@JsonIgnoreProperties(value = {"subscriptor"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subscriptor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubscriptorActividad> subscriptorActividad;
	
	@JsonIgnoreProperties(value = {"subscriptorId"}, allowSetters = true)
	//@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToOne
	@JoinColumn(name = "persona_id")
	private Persona personaId;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public SubscriptorDireccion getSubscriptoresDirecciones() {
		return subscriptoresDirecciones;
	}

	public void setSubscriptoresDirecciones(SubscriptorDireccion subscriptoresDirecciones) {
		this.subscriptoresDirecciones = subscriptoresDirecciones;
	}

	public TipoInscripcion getTipoInscripcion() {
		return tipoInscripcion;
	}

	public void setTipoInscripcion(TipoInscripcion tipoInscripcion) {
		this.tipoInscripcion = tipoInscripcion;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	public void addActividad(Actividad actividad) {
		this.actividades.add(actividad);
	}

	public void removeActividad(Actividad actividad) {
		this.actividades.remove(actividad);
	}


	public List<SubscriptorActividad> getSubscriptorActividad() {
		return subscriptorActividad;
	}

	public void setSubscriptorActividad(List<SubscriptorActividad> subscriptorActividad) {
		this.subscriptorActividad = subscriptorActividad;
	}

	public void addSubscriptorActividad(SubscriptorActividad subscriptorActividad) {
		this.subscriptorActividad.add(subscriptorActividad);
	}

	
	public void removeSubscriptorActividad(SubscriptorActividad subscriptorActividad) {
		this.subscriptorActividad.remove(subscriptorActividad);
	}

	public Persona getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Persona personaId) {
		this.personaId = personaId;
	}

	
	
}
