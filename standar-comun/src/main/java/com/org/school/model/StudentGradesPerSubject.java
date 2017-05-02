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
@Table(name = "STUDENTGRADESPERSUBJECT")
@SequenceGenerator(name = "SEQ_STUDENTGRADESPERSUBJECT", sequenceName = "SEQ_STUDENTGRADESPERSUBJECT", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class StudentGradesPerSubject implements BaseModelEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3859652101287409531L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STUDENTGRADESPERSUBJECT")
	@Column(nullable = false)
	private Long id;
	
	private Double firstGrade;
	
	private Double secondGrade;
	
	private Double thirdGrade;
	
	@ManyToOne
	@JoinColumn(name = "SUBJECTPERCOURSE_ID")
	private SubjectPerCourse subjectPerCourse;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
}
