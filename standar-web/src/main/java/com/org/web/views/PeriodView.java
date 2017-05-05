package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			changeVigenciaPeriod();
			newPeriod.setVigente(true);
			periodService.save(newPeriod);
			List<Trimesters> trimestres = createTrimesters(newPeriod);
			trimestersService.save(trimestres);
			Messages.create("Información").detail("Registro ingresado exitosamente").add();
		}
	}

	private void changeVigenciaPeriod() {
		List<Period> periodos = periodService.findAll();
		boolean isNotEmptyList = !periodos.isEmpty();
		if (isNotEmptyList) {
			for (Period period : periodos) {
				period.setVigente(false);
			}
			periodService.save(periodos);
		}
	}

	private List<Trimesters> createTrimesters(Period periodo) {
		List<Trimesters> trimestresPorPeriodo = new ArrayList<Trimesters>();
		for (int i = 0; i < 4; i++) {
			Trimesters trime = new Trimesters();
			switch (i) {
			case 0:
				trime.setName("TRIMESTRE I");
				trime.setMonths("Enero - Marzo");
				trime.setPeriod(periodo);
				break;
			case 1:
				trime.setName("TRIMESTRE II");
				trime.setMonths("Abril - Junio");
				trime.setPeriod(periodo);
				break;
			case 2:
				trime.setName("TRIMESTRE III");
				trime.setMonths("Julio - Septiembre");
				trime.setPeriod(periodo);
				break;
			case 3:
				trime.setName("TRIMESTRE IV");
				trime.setMonths("Octubre - Noviembre");
				trime.setPeriod(periodo);
				break;

			default:
				break;
			}
			trimestresPorPeriodo.add(trime);
		}
		return trimestresPorPeriodo;
	}

	public void updatePeriod() {
		if (isNotNullPeriodSelected()) {
			periodService.save(periodSelected);
			Messages.create("Información").detail("Registro actualizada exitosamente").add();
		}
	}

	public void deletePeriod() {
		if (isNotNullPeriodSelected()) {
			List<Trimesters> removeTrime = trimestersService.findTrimesterByPeriod(periodSelected);
			boolean isNotEmptyList = !removeTrime.isEmpty();
			if (isNotEmptyList) {
				for (Trimesters trimesters : removeTrime) {
					trimestersService.deleteOne(trimesters);
				}
			}

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
