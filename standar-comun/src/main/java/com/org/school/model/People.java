package com.org.school.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

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

	private Date dateOfBirth;

	private String address;

	private int isActive;

}
