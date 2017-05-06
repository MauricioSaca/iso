package com.org.school.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "ENTREGATAREAS")
@SequenceGenerator(name = "SEQ_ENTREGATAREAS", sequenceName = "SEQ_ENTREGATAREAS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class EntregaTareas implements BaseModelEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725531125738359064L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTREGATAREAS")
	@Column(nullable = false)
	private Long id;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fechaEntrega;
	
	@Column(name = "FILENAME")
	private String fileName;

	@Lob
	@Column(name = "ARCHIVO")
	private byte[] file;

	@ManyToOne
	@JoinColumn(name = "ASSIGMENTS_ID")
	private Assigments assigments;

	@ManyToOne
	@JoinColumn(name = "STUDENTSPERCOURSE_ID")
	private StudentsPerCourse studentsPerCourse;
}
