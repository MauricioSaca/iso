package com.org.web.indicadores.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.indicadores.enums.EvaluationPeriod;
import com.org.indicadores.enums.UnidadMedida;
import com.org.indicadores.model.Indicador;
import com.org.indicadores.service.IndicadorService;
import com.org.indicadores.service.MetasService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class IndicadorView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = -53453231239L;

	/** CDI INJECTION POINT */

	@Inject
	private transient IndicadorService indicadorService;

	@Inject
	private transient MetasService metasService;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Indicador, Long> lazyIndicadorModel;
	private Indicador newIndicador;
	private Indicador indicadorSelected;

	private List<UnidadMedida> unidadesMedidas;
	private List<EvaluationPeriod> evaluations;

	@PostConstruct
	public void init() {
		loadLazyDataModel();
		evaluations = Arrays.asList(EvaluationPeriod.values());
		unidadesMedidas = Arrays.asList(UnidadMedida.values());
	}

	private void loadLazyDataModel() {
		lazyIndicadorModel = new BaseLazyModel<Indicador, Long>(indicadorService);
	}

	public void preparedCreatedIndicador() {
		setStatus(ViewStatus.NEW);
		newIndicador = new Indicador();
	}

	public void preparedEditIndicador() {
		setStatus(ViewStatus.EDIT);
	}

	public void saveIndicador() {
		if (isNotNullNewIndicador()) {
			indicadorService.save(newIndicador);
			Messages.create("Información").detail("Registro ingresado exitosamente").add();
		}
	}

	public void updateIndicador() {
		if (isNotNullIndicadorSelected()) {
			indicadorService.save(indicadorSelected);
			Messages.create("Información").detail("Registro actualizada exitosamente").add();
		}
	}

	public void deleteIndicador() {
		if (isNotNullIndicadorSelected()) {
			if (metasService.isHasNotMetas(indicadorSelected)) {
				indicadorService.deleteOne(indicadorSelected);
				Messages.create("Información").detail("Registro eliminado exitosamente").add();
			} else {
				Messages.create("Error").detail("Contiene metas asignadas!!").error().add();
			}

		}
	}

	private boolean isNotNullNewIndicador() {
		return newIndicador != null;
	}

	private boolean isNotNullIndicadorSelected() {
		return indicadorSelected != null;
	}

}
