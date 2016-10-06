package com.org.adoption.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@SequenceGenerator(name = "SEQ_ADOPTER", sequenceName = "SEQ_ADOPTER", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id"})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Adopter implements BaseModelEntity<Long> {


	private static final long serialVersionUID = -764549576523L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADOPTER")
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true)
	private Boolean active;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "POSTULANT_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Postulant postulant;
	
	@OneToMany(mappedBy = "adopter" ,cascade = CascadeType.ALL)
	private Set<AdoptedPets> adoptedPets;
	
}
