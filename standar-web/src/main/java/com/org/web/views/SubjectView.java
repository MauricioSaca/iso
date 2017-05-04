package com.org.web.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.model.Subject;
import com.org.school.services.SubjectService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class SubjectView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 4324329879858L;

	/** CDI INJECTION POINT */
	@Inject
	private transient SubjectService subjectService;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Subject, Long> lazySubjectModel;
	private Subject newSubject;
	private Subject subjectSelected;

	@PostConstruct
	public void init() {
		loadLazyDataModels();
	}

	private void loadLazyDataModels() {
		lazySubjectModel = new BaseLazyModel<Subject, Long>(subjectService);
	}

	public void preparedCreatedSubject() {
		setStatus(ViewStatus.NEW);
		newSubject = new Subject();
	}

	public void preparedEditSubject() {
		setStatus(ViewStatus.EDIT);
	}

	public void saveSubject() {
		if (isNotNullNewSubject()) {
			subjectService.save(newSubject);
			Messages.create("Información").detail("Registro ingresado exitosamente").add();
		}
	}

	public void updateSubject() {
		if (isNotNullSubjectSelected()) {
			subjectService.save(subjectSelected);
			Messages.create("Información").detail("Registro actualizada exitosamente").add();
		}
	}

	public void deleteSubject() {
		if (isNotNullSubjectSelected()) {
			subjectService.deleteOne(subjectSelected);
			Messages.create("Información").detail("Registro eliminado exitosamente").add();
		}
	}

	private boolean isNotNullNewSubject() {
		return newSubject != null;
	}

	private boolean isNotNullSubjectSelected() {
		return subjectSelected != null;
	}

}
