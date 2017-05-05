package com.org.school.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "BEHAVIOUR_MONITORING")
@SequenceGenerator(name = "SEQ_BEHAVIOUR_MONITORING", sequenceName = "SEQ_BEHAVIOUR_MONITORING", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class BehaviourMonitoring implements BaseModelEntity<Long> {

	private static final long serialVersionUID = 987454345365476576L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BEHAVIOUR_MONITORING")
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "STUDENTSPERCOURSE_ID")
	private StudentsPerCourse studentsPerCourse;
	
	@ManyToOne
	@JoinColumn(name = "CATALOGOCODIGOS_ID")
	private CatalogoCodigos catalogoCodigos;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fechaAplicado;

}
