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
		
		if (isAdminUser) {
			adminMenu();
		}

		if (isPostulantUser) {
			postulanteMenu();
		}

		if (isManagerUser) {
			managerMenu();
		}

		if (isOrganizacionalUser) {
			organizacionalMenu();
		}

	}

	private void adminMenu() {
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Administración de seguridad");
		firstSubmenu.setIcon("icon-menu");

		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue("Lista de Usuarios");
		item.setTarget("/security/list.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue("Lista de grupos");
		item.setTarget("/security/group/list.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue("Lista de roles");
		item.setTarget("/security/role/list.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);

		DefaultSubMenu secondSubmenu = new DefaultSubMenu("Administración de indicadores");
		secondSubmenu.setIcon("icon-menu");

		item = new DefaultMenuItem();
		item.setValue("Lista de indicadores");
		item.setTarget("/indicadores/list.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		secondSubmenu.addElement(item);

		model.addElement(secondSubmenu);

		DefaultSubMenu thirdSubmenu = new DefaultSubMenu("Asignación de metas a indicadores");
		thirdSubmenu.setIcon("icon-menu");

		item = new DefaultMenuItem();
		item.setValue("Indicadores");
		item.setTarget("/metas/indicador_list.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		thirdSubmenu.addElement(item);

		model.addElement(thirdSubmenu);
	}

	private void postulanteMenu() {
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Administración");
		firstSubmenu.setIcon("icon-menu");

		DefaultMenuItem item = new DefaultMenuItem();

		item.setValue(" Buscar");
		item.setTarget("/postulante/buscar.xhtml");
		item.setIcon("icon-search");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);
		
		item = new DefaultMenuItem();
		item.setValue(" Perfil");
		item.setTarget("/postulante/perfil.xhtml");
		item.setIcon("icon-hyperlink");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue(" Solicitudes");
		item.setTarget("/postulante/solicitudes.xhtml");
		item.setIcon("fa fa-list-alt");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue(" Cuestionario");
		item.setTarget("/postulante/cuestionario.xhtml");
		item.setIcon("icon-book");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);
	}

	private void organizacionalMenu() {

		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Menú");
		firstSubmenu.setIcon("icon-menu");

		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue(" Mascotas");
		item.setTarget("/mascotas/mascotas.xhtml");
		item.setIcon("icon-feather");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue(" Adoptantes");
		item.setTarget("/mascotas/adoptantes.xhtml");
		item.setIcon("fa fa-folder-open-o");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		item = new DefaultMenuItem();
		item.setValue(" Solicitudes de Adopcion");
		item.setTarget("/mascotas/solicitudes.xhtml");
		item.setIcon("fa fa-folder-open-o");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);
		
		item = new DefaultMenuItem();
		item.setValue("Cuestionario");
		item.setTarget("/mascotas/preguntas.xhtml");
		item.setIcon("icon-book");
		item.setCommand("#{securityController.onMenuSelect}");
		firstSubmenu.addElement(item);

		model.addElement(firstSubmenu);
	}

	private void managerMenu() {
		DefaultSubMenu firstSubmenu = new DefaultSubMenu("Verificación de metas");
		firstSubmenu.setIcon("icon-menu");

		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue("Indicador/Metas");
		item.setTarget("/metas_compativos/list_indicador_metas.xhtml");
		item.setIcon("fa fa-list-alt");
		item.setCommand("#{securityController.onMenuSelect}");
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
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		String urlWithSessionID = response.encodeRedirectURL(menu.getTarget());
		String uri = facesContext.getExternalContext().getRequestContextPath() + urlWithSessionID;
		facesContext.getExternalContext().redirect(uri);
	}

}
