package com.org.web.metas.view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.indicadores.enums.MensualEnum;
import com.org.indicadores.enums.TrimestralEnum;
import com.org.indicadores.model.Indicador;
import com.org.indicadores.model.Metas;
import com.org.indicadores.service.IndicadorService;
import com.org.indicadores.service.MetasService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.safe.ValueHolder;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class MetasView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = -4987986765L;

	/** CDI INJECTION POINT */

	@Inject
	private transient IndicadorService indicadorService;

	@Inject
	private transient MetasService metasService;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Indicador, Long> lazyIndicadorModel;
	private transient BaseLazyModel<Metas, Long> lazyMetasModel;

	private Indicador indicadorSelected;
	private Metas newMetaForIndicador;
	private Metas metaSelected;

	private List<TrimestralEnum> trimestres;
	private List<MensualEnum> meses;

	@PostConstruct
	public void init() {
		loadLazyDataModels();
		trimestres = Arrays.asList(TrimestralEnum.values());
		meses = Arrays.asList(MensualEnum.values());
	}

	private void loadLazyDataModels() {
		lazyIndicadorModel = new BaseLazyModel<Indicador, Long>(indicadorService);
	}

	public void preparedCreatedMetas() {
		setStatus(ViewStatus.NEW);
		lazyMetasModel = new BaseLazyModel<Metas, Long>(metasService);
		lazyMetasModel.setCustomFilters(filter());
		newMetaForIndicador = new Metas();
	}

	public void preparedEditMeta() {
		setStatus(ViewStatus.EDIT);
	}

	private Set<ValueHolder> filter() {
		Set<ValueHolder> filtros = new HashSet<>();
		ValueHolder customFilter = new ValueHolder("indicador.id", indicadorSelected.getId());
		filtros.add(customFilter);
		return filtros;
	}

	public void persitMeta() {
		if (ViewStatus.NEW.equals(getStatus())) {
			saveMeta();
		} else if (ViewStatus.EDIT.equals(getStatus())) {
			updateMeta();
		}
	}

	private void saveMeta() {
		if (isNotNUllNewMeta()) {
			if (metasService.isNotExistMetaByOrdinariDate(newMetaForIndicador.getOrdinalDate())) {
				newMetaForIndicador.setIndicador(indicadorSelected);
				metasService.save(newMetaForIndicador);
				newMetaForIndicador = new Metas();
				Messages.create("Información").detail("Registro ingresado exitosamente").add();
			} else {
				Messages.create("Error").detail("Meta ya asignada!!").error().add();
			}
		}
	}

	private void updateMeta() {
		if (isNotNullSelectedMeta()) {
			metasService.save(metaSelected);
			metaSelected = new Metas();
			setStatus(ViewStatus.NEW);
			Messages.create("Información").detail("Registro actualizado exitosamente").add();
		}
	}

	public void deleteMeta() {
		if (isNotNullSelectedMeta()) {
			metasService.deleteOne(metaSelected);
			Messages.create("Información").detail("Registro eliminado exitosamente").add();
		}
	}

	public String getTrimestre(String code) {
		return TrimestralEnum.getTrimestralEnum(code).getDescription();
	}

	public String getMes(String code) {
		return MensualEnum.getMensualEnum(code).getDescription();
	}

	private boolean isNotNUllNewMeta() {
		return newMetaForIndicador != null;
	}

	private boolean isNotNullSelectedMeta() {
		return metaSelected != null;
	}

}
