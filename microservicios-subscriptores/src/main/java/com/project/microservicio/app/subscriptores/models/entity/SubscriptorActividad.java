package com.project.microservicio.app.subscriptores.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subscriptores_actividades")
public class SubscriptorActividad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "actividad_id", unique = true)
	private Long actividadId;
	
	@JsonIgnoreProperties(value = {"subscriptorActividad"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriptor_id")
	private Subscriptor subscriptor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActividadId() {
		return actividadId;
	}

	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}

	public Subscriptor getSubscriptor() {
		return subscriptor;
	}

	public void setSubscriptor(Subscriptor subscriptor) {
		this.subscriptor = subscriptor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof SubscriptorActividad)) {
			return false;
		}
		
		SubscriptorActividad a = (SubscriptorActividad) obj;
		
		return this.actividadId != null && this.actividadId.equals(a.getActividadId());
	}
}
