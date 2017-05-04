package com.org.web.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.model.Period;
import com.org.school.model.Trimesters;
import com.org.school.services.PeriodService;
import com.org.school.services.TrimestersService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PeriodView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 7571230981288L;

	/** CDI INJECTION POINT */

	@Inject
	private transient PeriodService periodService;

	@Inject
	private transient TrimestersService trimestersService;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Period, Long> lazyPeriodModel;

	private Period newPeriod;
	private Period periodSelected;

	private transient BaseLazyModel<Trimesters, Long> lazyTrimeModel;

	private Trimesters newTrime;
	private Trimesters trimeSelected;

	@PostConstruct
	public void init() {
		loadLazyDataModels();
	}

	private void loadLazyDataModels() {
		lazyPeriodModel = new BaseLazyModel<Period, Long>(periodService);
	}

	public void preparedCreatedPeriod() {
		setStatus(ViewStatus.NEW);
		newPeriod = new Period();
	}

	public void preparedEditPeriod() {
		setStatus(ViewStatus.EDIT);
	}

	public void savePeriod() {
		if (isNotNullNewPeriod()) {
			periodService.save(newPeriod);
			Messages.create("Información").detail("Registro ingresado exitosamente").add();
		}
	}

	public void updatePeriod() {
		if (isNotNullPeriodSelected()) {
			periodService.save(periodSelected);
			Messages.create("Información").detail("Registro actualizada exitosamente").add();
		}
	}

	public void deletePeriod() {
		if (isNotNullPeriodSelected()) {
			periodService.deleteOne(periodSelected);
			Messages.create("Información").detail("Registro eliminado exitosamente").add();
		}
	}

	private boolean isNotNullNewPeriod() {
		return newPeriod != null;
	}

	private boolean isNotNullPeriodSelected() {
		return periodSelected != null;
	}

}
