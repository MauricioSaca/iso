package com.org.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.org.util.domain.BaseModelEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SUBJECT")
@SequenceGenerator(name = "SEQ_SUBJECT", sequenceName = "SEQ_SUBJECT", allocationSize = 1)
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString(of = { "id" })
@Getter
@Setter
public class Subject implements BaseModelEntity<Long>{
	
	private static final long serialVersionUID = 830254835292L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBJECT")
	@Column(nullable = false)
	private Long id;
	
	private String code;
	
	private String name;
	
}
