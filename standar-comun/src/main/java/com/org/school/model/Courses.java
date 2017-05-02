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
@Table(name = "COURSES")
@SequenceGenerator(name = "SEQ_COURSES", sequenceName = "SEQ_COURSES", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Courses implements BaseModelEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8304857403626065616L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COURSES")
	@Column(nullable = false)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	private Teacher teacher;
}
