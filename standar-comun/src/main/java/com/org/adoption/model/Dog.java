package com.org.adoption.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_DOG", sequenceName = "SEQ_DOG", allocationSize = 1)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(of = {"id"})
@Getter
@Setter
public class Dog extends Pet implements BaseModelEntity<Long>{


	private static final long serialVersionUID = -64564576432L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOG")
	@Column(nullable = false)
	private Long id;
	
	@Column(length = 255, nullable = false)
	private String dogName;
	
	@OneToMany(mappedBy = "dog" ,cascade = CascadeType.ALL)
	private Set<MedicalControl> medicalControlDog;

}
