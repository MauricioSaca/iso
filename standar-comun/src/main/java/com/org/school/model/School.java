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
@Table(name = "SCHOOL")
@SequenceGenerator(name = "SEQ_SCHOOL", sequenceName = "SEQ_SCHOOL", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class School implements BaseModelEntity<Long> {

	private static final long serialVersionUID = -376875654L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCHOOL")
	@Column(nullable = false)
	private Long id;

	private String code;

	private String name;

	private String foundationYear;

	private Boolean isActive;

	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "MUNICIPAL_ID")
	private Municipal municipio;

}
