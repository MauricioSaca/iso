package com.org.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.school.enums.StudentLevel;
import com.org.school.enums.StudentStatus;
import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "STUDENT")
@SequenceGenerator(name = "SEQ_STUDENT", sequenceName = "SEQ_STUDENT", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Student extends People implements BaseModelEntity<Long> {

	private static final long serialVersionUID = 845388676722453L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STUDENT")
	@Column(nullable = false)
	private Long id;

	@Column(length = 255, nullable = true)
	private String studentStatus;

	@Column(length = 255, nullable = true)
	private String studentLevel;

	/**
	 * Relations By Enums
	 **/
	public StudentStatus getStudentStatus() {
		return StudentStatus.getStudentStatus(this.studentStatus);
	}

	public void setStudentStatus(StudentStatus studentStatus) {
		this.studentStatus = studentStatus != null ? studentStatus.getCode() : null;
	}

	public StudentLevel getStudentLevel() {
		return StudentLevel.getStudentLevel(this.studentLevel);
	}

	public void setStudentLevel(StudentLevel studentLevel) {
		this.studentLevel = studentLevel != null ? studentLevel.getCode() : null;
	}

}
