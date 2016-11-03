package com.org.security.web.views;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

import com.org.security.enums.GroupsSecurityRolesNames;
import com.org.security.utils.AuthorizationChecker;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Named
@SessionScoped
@Setter
@Getter
@Log
public class SecurityController implements Serializable {

	private static final long serialVersionUID = -17312982688234323L;

	@Inject
	private Identity identity;

	@Inject
	private FacesContext facesContext;

	@Inject
	private DefaultLoginCredentials loginCredentials;

	@Inject
	private AuthorizationChecker authorizationChecker;

	private MenuModel model;

	@PostConstruct
	public void init() {
		model = new DefaultMenuModel();

		boolean isAdminUser = authorizationChecker.hasGroup(GroupsSecurityRolesNames.ADMINS.getCode());
		boolean isPostulantUser = authorizationChecker.hasGroup(GroupsSecurityRolesNames.POSTULANDS.getCode());
		boolean isManagerUser = authorizationChecker.hasGroup(GroupsSecurityRolesNames.MANAGERS.getCode());
		boolean isOrganizacionalUser = authorizationChecker.hasGroup(GroupsSecurityRolesNames.ORGANIZERS.getCode());
		boolean isReportUser = authorizationChecker.hasGroup(GroupsSecurityRolesNames.REPORTS.getCode());

		if (isAdminUser) {
			adminMenu();
		}

		if (isPostulantUser) {

		}

		if (isManagerUser) {

		}

		if (isOrganizacionalUser) {

		}

		if (isReportUser) {

		}
	}

	private void adminMenu() {
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Usuarios");
		firstSubmenu.setIcon("icon-menu");
		
		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue("crear");
		item.setTarget("direccion");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		item.setFragment("1");

		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);
	}

	public MenuModel getMenuModel() {
		try {
			return model;
		} catch (Exception e) {
			return new DefaultMenuModel();
		}
	}

	public void onMenuSelect(ActionEvent actionEvent) throws IOException {
		MenuItem menu = ((MenuActionEvent) actionEvent).getMenuItem();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext()
				.getResponse();

		String urlWithSessionID = response.encodeRedirectURL(menu.getTarget());
		facesContext.getExternalContext().redirect(urlWithSessionID);
	}

}
