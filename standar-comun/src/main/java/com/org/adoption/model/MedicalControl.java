package com.org.adoption.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table()
@SequenceGenerator(name = "SEQ_MEDICAL_CONTROL" ,sequenceName = "SEQ_MEDICAL_CONTROL", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id"})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class MedicalControl implements BaseModelEntity<Long>{

	
	private static final long serialVersionUID = -75465465465484320L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEDICAL_CONTROL")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = false)
	private String doctorName;

	@Column(length = 255, nullable = false)
	private String descriptionCondition;

	@Column(nullable = true)
	private Boolean tookVitamins;

	@Column(nullable = true)
	private Boolean isVaccunated;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date actualDay;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date nextControl;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "DOG_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Dog dog;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CAT_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Cat cat;

}
