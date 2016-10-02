package com.org.web.security.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import com.org.security.model.User;
import com.org.security.service.UserService;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class UserView implements Serializable {

	private static final long serialVersionUID = 7294268880729237565L;

	private String userName;
	private String password;

	@Inject
	private transient UserService usuarioService;

	private User usuario;

	@PostConstruct
	public void init() {
		usuario = new User();
		usuario = usuarioService.findOne(new Long(1));
	}

	public void actionLogin() {
		if (usuario != null) {
			if (isNotNullAndNotEmpty(userName) && isNotNullAndNotEmpty(password)) {
				if (usuario.getIsEnabled()) {
					if (userName.equals(usuario.getLogin().getUserName())
							&& password.equals(usuario.getLogin().getPassword())) {
						
						String uri = "/index.xhtml";
						
						FacesContext.getCurrentInstance().getApplication()
								.getNavigationHandler()
								.handleNavigation(FacesContext.getCurrentInstance(), null, uri);
					}
				}
			}
		}
	}

	private boolean isNotNullAndNotEmpty(String value) {
		return value != null && !StringUtils.EMPTY.equals(value);
	}

}
