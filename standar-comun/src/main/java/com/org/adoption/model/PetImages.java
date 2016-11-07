package com.org.adoption.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_PETIMG", sequenceName = "SEQ_PETIMG", allocationSize = 1)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(of = {"id"})
@Getter
@Setter
public class PetImages implements BaseModelEntity<Long>{

	private static final long serialVersionUID = 987098408L;
	
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PETIMG")
	@Column(nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PET_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Pet pet;
	
	@Column(nullable = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] img;

	public PetImages() {

	}
	
	public PetImages(Pet pet, byte[] img) {
		super();
		this.pet = pet;
		this.img = img;
	}

}
