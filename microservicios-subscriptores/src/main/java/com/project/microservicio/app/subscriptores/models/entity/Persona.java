package com.project.microservicio.app.subscriptores.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "personas")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name")
	@NotNull
	@NotBlank
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
	@Transient
	private String confirmPassword;

	@JsonIgnoreProperties(value = {"personaId"}, allowSetters = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empleado_id")
	private Empleado empleadoId;
	
	//@OneToOne
	@JsonIgnoreProperties(value = {"personaId"}, allowSetters = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subscriptor_id")
	private Subscriptor subscriptorId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public Empleado getEmpleadoId() {
		return empleadoId;
	}
	
	public void setEmpleadoId(Empleado empleadoId) {
		this.empleadoId = empleadoId;
	}
	
	public Subscriptor getSubscriptorId() {
		return subscriptorId;
	}
	
	public void setSubscriptorId(Subscriptor subscriptorId) {
		this.subscriptorId = subscriptorId;
	}
	
}
