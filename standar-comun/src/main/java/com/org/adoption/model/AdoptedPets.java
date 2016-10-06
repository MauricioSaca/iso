package com.org.adoption.model;

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
@SequenceGenerator(name = "SEQ_ADOPTED_PETS", sequenceName = "SEQ_ADOPTED_PETS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" })
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class AdoptedPets implements BaseModelEntity<Long>{


	private static final long serialVersionUID = 2432342365756765L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADOPTED_PETS")
	@Column(nullable = false)
	Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "DOG_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Dog dog;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CAT_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Cat cat;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "ADOPTER_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private Adopter adopter;

}
