package com.org.security.web.views;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;

import lombok.Getter;
import lombok.Setter;

@Named
@Stateless
@Getter
@Setter
public class LoginController implements Serializable {

	private static final long serialVersionUID = -72942234249237565L;
	private static final String INDEX = "/index.xhtml";
	private static final String LOGIN = "/login.xhtml";

	@Inject
	private Identity identity;

	@Inject
	private FacesContext facesContext;

	public String login() throws IOException {
		if (identity.isLoggedIn()) {
			facesContext.addMessage(null, new FacesMessage("A user is loginin."));
			ExternalContext context = facesContext.getExternalContext();
			context.getFlash().setKeepMessages(true);
			context.redirect(context.getRequestContextPath() + INDEX);
		} else {
			AuthenticationResult result = identity.login();
			if (AuthenticationResult.FAILED.equals(result)) {
				facesContext.addMessage(null, new FacesMessage("Invalid username or password."));
			} else {
				ExternalContext context = facesContext.getExternalContext();
				context.redirect(context.getRequestContextPath() + INDEX);
			}
		}
		return null;
	}

	public void logout() throws IOException {
		identity.logout();
		ExternalContext context = facesContext.getExternalContext();
		context.redirect(context.getRequestContextPath() + LOGIN);
	}

}
