package com.org.web.security.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.picketlink.idm.credential.Password;

import com.org.security.identity.stereotype.Group;
import com.org.security.identity.stereotype.Role;
import com.org.security.identity.stereotype.User;
import com.org.security.service.SecurityManagedService;
import com.org.security.service.SecurityValidate;
import com.org.util.enumeration.ViewStatus;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class UserView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = -745345098098L;

	/** CDI INJECTION POINT */
	@Inject
	private transient SecurityManagedService securityManagedService;

	@Inject
	private transient SecurityValidate securityValidate;

	@Inject
	private FacesContext facesContext;

	/** INSTANCE FIELD */

	private User newUser;
	private User userSelected;
	
	private Password userPassword;
	private String password;
	private String passwordConfirm;

	private Group userGroup;
	private Role userRole;

	private List<User> users;
	private List<Group> groups;
	private List<Role> roles;

	@PostConstruct
	public void init() {
		users = new ArrayList<User>();
		users = securityManagedService.findAllUser();

		groups = new ArrayList<Group>();
		groups = securityManagedService.findAllGroup();

		roles = new ArrayList<Role>();
		roles = securityManagedService.findAllRole();
	}

	public void preparedCreatedUser() {
		setStatus(ViewStatus.NEW);
		newUser = new User(StringUtils.EMPTY);
	}

	public void saveUser() {
		boolean isNotExistGroup = !securityValidate.existUser(newUser.getUserName());
		if (isNotExistGroup) {
			if (password.equals(passwordConfirm)) {
				userPassword = new Password(password);
				securityManagedService.saveUser(newUser, userPassword, userGroup, userRole);
				init();
			}
		} else {
			facesContext.addMessage(null, new FacesMessage("Usuario ya existe"));
		}
	}
}
