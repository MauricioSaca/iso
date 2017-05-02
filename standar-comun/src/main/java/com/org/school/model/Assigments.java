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

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ASSIGMENTS")
@SequenceGenerator(name = "SEQ_ASSIGMENTS", sequenceName = "SEQ_ASSIGMENTS", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Assigments implements BaseModelEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725531125738359064L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ASSIGMENTS")
	@Column(nullable = false)
	private Long id;
	
	private String name;
	
	private String description;
	
	private Date dueDate;
	
	@ManyToOne
	@JoinColumn(name = "SUBJECTPERCOURSE_ID")
	private SubjectPerCourse subjectPerCourse;
}
