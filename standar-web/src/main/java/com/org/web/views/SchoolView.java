package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.model.Department;
import com.org.school.model.Municipal;
import com.org.school.model.School;
import com.org.school.services.DepartmentService;
import com.org.school.services.MunicipalService;
import com.org.school.services.SchoolService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class SchoolView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 59831273981L;

	/** CDI INJECTION POINT */
	@Inject
	private transient SchoolService schoolService;

	@Inject
	private transient DepartmentService departmentService;

	@Inject
	private transient MunicipalService municipalService;

	@Inject
	private FacesContext facesContext;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<School, Long> lazySchoolModel;
	private School newSchool;
	private School schoolSelected;

	private List<Department> departmentList;
	private Department departmentSelected;
	private List<Municipal> municipalList;
	private Municipal municipalSelected;

	@PostConstruct
	public void init() {
		loadLazyDataModels();
	}

	private void loadLazyDataModels() {
		lazySchoolModel = new BaseLazyModel<School, Long>(schoolService);
		departmentList = new ArrayList<Department>();
		departmentList = departmentService.findAll();
	}

	public void preparedCreatedSchool() {
		setStatus(ViewStatus.NEW);
		newSchool = new School();
	}

	public void preparedEditSchool() {
		setStatus(ViewStatus.EDIT);
	}

	public void saveSchool() {
		if (isNotNullNewSchool()) {
			if (isNotNullDepartamentAndMunicipal()) {
				newSchool.setIsActive(true);
				newSchool.setDepartment(departmentSelected);
				newSchool.setMunicipio(municipalSelected);
				schoolService.save(newSchool);
				Messages.create("Información").detail("Registro ingresado exitosamente").add();
			}
		}
	}

	public void updateSchool() {
		if (isNotNullSchoolSelected()) {
			if (isNotNullDepartamentAndMunicipal()) {
				schoolSelected.setDepartment(departmentSelected);
				schoolSelected.setMunicipio(municipalSelected);
				schoolService.save(schoolSelected);
				Messages.create("Información").detail("Registro actualizada exitosamente").add();
			}
		}
	}

	public void deleteSchool() {
		if (isNotNullSchoolSelected()) {
			schoolService.deleteOne(schoolSelected);
			Messages.create("Información").detail("Registro eliminado exitosamente").add();
		}
	}

	public void changeDepartmentEvent() {
		if (departmentSelected != null) {
			municipalList = municipalService.findMunicipalByDepartament(departmentSelected);
		}
	}

	private boolean isNotNullNewSchool() {
		return newSchool != null;
	}

	private boolean isNotNullSchoolSelected() {
		return schoolSelected != null;
	}

	private boolean isNotNullDepartamentAndMunicipal() {
		return departmentSelected != null && municipalSelected != null;
	}

}
