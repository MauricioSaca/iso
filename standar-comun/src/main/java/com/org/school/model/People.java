package com.org.school.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.org.school.enums.Gender;
import com.org.school.enums.Shift;
import com.org.security.identity.model.UserTypeEntity;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Table
@Getter
@Setter
public class People implements Serializable {

	private static final long serialVersionUID = 2956630905619789036L;

	private String name;

	private String lastName;

	private int age;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	private String address;
	
	private String email;
	
	private String cellPhone;

	private int isActive;

	@ManyToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = true)
	private School school;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private UserTypeEntity userTypeEntity;
	
	@Column(length = 255, nullable = true)
	private String gender;

	@Column(length = 255, nullable = true)
	private String shift;
	
	

	/**
	 * Relations By Enums
	 **/
	public Gender getGender() {
		return Gender.getGender(this.gender);
	}

	public void setGender(Gender gender) {
		this.gender = gender != null ? gender.getCode() : null;
	}

	public Shift getShift() {
		return Shift.getShift(this.shift);
	}

	public void setShift(Shift shift) {
		this.shift = shift != null ? shift.getCode() : null;
	}

}
