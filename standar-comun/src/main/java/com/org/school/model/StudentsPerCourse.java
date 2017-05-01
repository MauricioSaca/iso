package com.org.school.model;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "STUDENTSPERCOURSE")
@SequenceGenerator(name = "SEQ_STUDENTSPERCOURSE", sequenceName = "SEQ_STUDENTSPERCOURSE", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class StudentsPerCourse implements BaseModelEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6402619196775092408L;

	private Long id;
	
	
}
