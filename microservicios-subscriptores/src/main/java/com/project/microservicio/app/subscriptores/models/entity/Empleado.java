package com.project.microservicio.app.subscriptores.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "empleados")
public class Empleado  extends CommonData{
	

	@JsonIgnoreProperties(value = {"empleado"}, allowSetters = true)
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
	private EmpleadoDireccion empleadosDirecciones;

	@Column(name = "tipo_empleado", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TipoEmpleado tipoEmpleado;
	
	@JsonIgnoreProperties(value = {"empleadoId"}, allowSetters = true)
	@OneToOne
	@JoinColumn(name = "persona_id")
	private Persona personaId;
	
	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	public EmpleadoDireccion getEmpleadosDirecciones() {
		return empleadosDirecciones;
	}

	public void setEmpleadosDirecciones(EmpleadoDireccion empleadosDirecciones) {
		this.empleadosDirecciones = empleadosDirecciones;
	}

	public Persona getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Persona personaId) {
		this.personaId = personaId;
	}

	
}
