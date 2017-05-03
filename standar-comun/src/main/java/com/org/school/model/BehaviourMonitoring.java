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

import com.org.school.enums.Behaviour;
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

	private String description;

	@Column(length = 255, nullable = true)
	private String behaviour;

	@ManyToOne
	@JoinColumn(name = "STUDENTSPERCOURSE_ID")
	private StudentsPerCourse studentsPerCourse;

	/**
	 * Relations By Enums
	 **/
	public Behaviour getBehaviour() {
		return Behaviour.getBehaviour(this.behaviour);
	}

	public void setStudentStatus(Behaviour behaviour) {
		this.behaviour = behaviour != null ? behaviour.getCode() : null;
	}

}
