package com.org.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRIMESTERS")
@SequenceGenerator(name = "SEQ_TRIMESTERS", sequenceName = "SEQ_TRIMESTERS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Trimesters implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -2342347666444L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRIMESTERS")
	@Column(nullable = false)
	private Long id;

	private String name;

	private String months;

	@ManyToOne
	@JoinColumn(name = "PERIOD_ID")
	private Period period;

}
