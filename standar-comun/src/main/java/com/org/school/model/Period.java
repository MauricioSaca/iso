package com.org.school.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PERIOD")
@SequenceGenerator(name = "SEQ_PERIOD", sequenceName = "SEQ_PERIOD", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Period implements BaseModelEntity<Long> {

	private static final long serialVersionUID = 4235439872394L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERIOD")
	@Column(nullable = false)
	private Long id;

	private String name;

	private Boolean vigente;

	@Column
	@Temporal(TemporalType.DATE)
	private Date initialDate;

	@Column
	@Temporal(TemporalType.DATE)
	private Date finalDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "period")
	private Set<Trimesters> trimesters;

}
