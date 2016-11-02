package com.org.adoption.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Table
@Getter
@Setter
public class Pet implements Serializable {

	private static final long serialVersionUID = 987098408L;

	@Column(length = 255, nullable = false)
	private String breed;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private Double weight;

	@Column(nullable = false)
	private Double height;

	@Column(length = 255, nullable = false)
	private String behavior;

	@Column(length = 255, nullable = false)
	private String description;

	@Column(length = 255, nullable = true)
	private String petOrigin;
	
	@Column(length = 255, nullable = true)
	private String petStatus;
	
	@Column(length = 255, nullable = true)
	private String petGender;
	
	//Relacion por enum
	
	public PetOrigin getPetOrigin () {
		return PetOrigin.getPetOrigin(this.petOrigin);
	}
	
	public void setPetOrigin (PetOrigin origin) {
		this.petOrigin = origin != null ? origin.getCode() : null;
	}
	
	public PetStatus getPetStatus () {
		return PetStatus.getPetStatus(this.petStatus);
	}
	
	public void setPetStatus(PetStatus status) {
		this.petStatus = status != null ? status.getCode() : null;
	}
	
	public PetGender getPetGender () {
		return PetGender.getPetGender(this.petGender);
	}
	
	public void setPetGender(PetGender gender) {
		this.petGender = gender != null ? gender.getCode() : null;
	}
	
}
