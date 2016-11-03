package com.org.adoption.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;
import com.org.core.model.enums.PetType;
import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_PET", sequenceName = "SEQ_PET", allocationSize = 1)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(of = {"id"})
@Getter
@Setter
public class Pet implements BaseModelEntity<Long>{

	private static final long serialVersionUID = 987098408L;
	
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PET")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String petName;
	
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
	private String petType;
	
	@Column(length = 255, nullable = true)
	private String petOrigin;
	
	@Column(length = 255, nullable = true)
	private String petStatus;
	
	@Column(length = 255, nullable = true)
	private String petGender;
	
	//Relacion por enum
	
	public PetType getPetType () {
		return PetType.getPetType(this.petType);
	}
	
	public void setPetType (PetType type) {
		this.petType = type != null ? type.getCode() : null;
	}
	
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
