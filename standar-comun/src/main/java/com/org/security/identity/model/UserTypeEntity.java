package com.org.security.identity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.jpa.annotations.OwnerReference;
import org.picketlink.idm.jpa.annotations.entity.IdentityManaged;
import org.picketlink.idm.model.annotation.AttributeProperty;

import com.org.security.identity.stereotype.User;

import lombok.Getter;
import lombok.Setter;

@IdentityManaged(User.class)
@Entity
@Getter
@Setter
public class UserTypeEntity extends AbstractIdentityTypeEntity {

	@AttributeValue
	private String userName;

	@OwnerReference
	@ManyToOne(fetch = FetchType.LAZY)
	private RealmTypeEntity realm;

	@AttributeProperty
	private String firstName;

	@AttributeProperty
	private String lastName;

	@AttributeProperty
	private String email;

	@AttributeProperty
	@Column(length = 255)
	private String middleName;

	@AttributeProperty
	@Size(max = 12)
	@Column(length = 12)
	private String telephone;

	@AttributeProperty
	@Column(length = 5000)
	@Size(max = 5000)
	private String address;

	@AttributeProperty
	private int postIndex;

	@AttributeProperty
	private Date lastVisitDate;

	@AttributeProperty
	private boolean isOrganizer;

	@AttributeProperty
	private boolean isAdmin;

}
