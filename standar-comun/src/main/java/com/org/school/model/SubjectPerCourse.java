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
@Table(name = "SUBJECTPERCOURSE")
@SequenceGenerator(name = "SEQ_SUBJECTPERCOURSE", sequenceName = "SEQ_SUBJECTPERCOURSE", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class SubjectPerCourse implements BaseModelEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4535072605550654743L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBJECTPERCOURSE")
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PERIOD_ID")
	private Period period;

	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "COURSES_ID")
	private Courses courses;
}
