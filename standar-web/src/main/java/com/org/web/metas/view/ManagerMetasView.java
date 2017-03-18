package com.org.web.metas.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ManagerMetasView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = -9875412388787L;

	/** CDI INJECTION POINT */

//	@Inject
//	private transient IndicadorService indicadorService;
//
//	@Inject
//	private transient MetasService metasService;
//
//	/** INSTANCE FIELD */
//
//	private transient BaseLazyModel<Indicador, Long> lazyIndicadorModel;
//	private transient BaseLazyModel<Metas, Long> lazyMetasModel;
//
//	private BarChartModel barModel;
//	private List<Metas> metasChartsData;
//
//	private Indicador indicadorSelected;
//	private Metas metaSelected;
//	private List<EvaluationLevel> evaluations;
//
//	@PostConstruct
//	public void init() {
//		loadLazyDataModels();
//		barModel = new BarChartModel();
//		evaluations = Arrays.asList(EvaluationLevel.values());
//	}
//
//	public void preparedMetas() {
//		setStatus(ViewStatus.NEW);
//		lazyMetasModel = new BaseLazyModel<Metas, Long>(metasService);
//		lazyMetasModel.setCustomFilters(filter());
//		metasChartsData = metasService.findMetasByIndicador(indicadorSelected);
//		initialBarModel();
//	}
//
//	private void loadLazyDataModels() {
//		lazyIndicadorModel = new BaseLazyModel<Indicador, Long>(indicadorService);
//	}
//	
//	public void preparedUpdateMeta(){
//		setStatus(ViewStatus.EDIT);
//	}
//	
//	public void updateMeta(){
//		if (isNotNullMetaSelected()) {
//			metasService.save(metaSelected);
//			initialBarModel();
//			Messages.create("Información").detail("Registro actualizada exitosamente").add();
//		}
//	}
//
//	private Set<ValueHolder> filter() {
//		Set<ValueHolder> filtros = new HashSet<>();
//		ValueHolder customFilter = new ValueHolder("indicador.id", indicadorSelected.getId());
//		filtros.add(customFilter);
//		return filtros;
//	}
//
//	private void initialBarModel() {
//		barModel = createBarModel();
//
//		barModel.setTitle(indicadorSelected.getName());
//		barModel.setLegendPosition("ne");
//		barModel.setMouseoverHighlight(true);
//		barModel.setShowDatatip(false);
//		barModel.setShowPointLabels(true);
//		
//		Axis xAxis = barModel.getAxis(AxisType.X);
//		xAxis.setLabel("Período");
//
//		Axis yAxis = barModel.getAxis(AxisType.Y);
//		yAxis.setLabel("Meta");
//		yAxis.setMin(0);
//		yAxis.setMax(200);
//	}
//	
//	private BarChartModel createBarModel() {
//		BarChartModel model = new BarChartModel();
//		
//		ChartSeries serie = new ChartSeries();
//		ChartSeries serieVigente = new ChartSeries();
//		
//		Map<Object, Number> dataProyectada = new LinkedHashMap<>();
//		Map<Object, Number> dataVigente = new LinkedHashMap<>();
//		
//		for (Metas meta : metasChartsData) {
//			
//			if (EvaluationPeriod.ANUAL.equals(meta.getIndicador().getEvaluationPeriod())) {
//				
//				dataProyectada.put(meta.getOrdinalDate(), meta.getMeta());
//				
//				Double metaResultado = meta.getResultadoMeta() == null ? 0 : meta.getResultadoMeta();
//				dataVigente.put(meta.getOrdinalDate(), metaResultado);
//			}
//
//			if (EvaluationPeriod.TRIMESTRAL.equals(meta.getIndicador().getEvaluationPeriod())) {
//				dataProyectada.put(TrimestralEnum.getTrimestralEnum(meta.getOrdinalDate()).getDescription(), meta.getMeta());
//				
//				Double metaResultado = meta.getResultadoMeta() == null ? 0 : meta.getResultadoMeta();
//				dataVigente.put(TrimestralEnum.getTrimestralEnum(meta.getOrdinalDate()).getDescription(), metaResultado);
//			}
//
//			if (EvaluationPeriod.MENSUAL.equals(meta.getIndicador().getEvaluationPeriod())) {
//				dataProyectada.put(MensualEnum.getMensualEnum(meta.getOrdinalDate()).getDescription(), meta.getMeta());
//				
//				Double metaResultado = meta.getResultadoMeta() == null ? 0 : meta.getResultadoMeta();
//				dataVigente.put(MensualEnum.getMensualEnum(meta.getOrdinalDate()).getDescription(), metaResultado);
//			}
//		}
//		
//		serie.setLabel("Proyectada");
//		serie.setData(dataProyectada);
//		
//		serieVigente.setLabel("Vigente");
//		serieVigente.setData(dataVigente);
//		
//		model.addSeries(serie);
//		model.addSeries(serieVigente);
//		
//		return model;
//	}
//
//	public String getTrimestre(String code) {
//		return TrimestralEnum.getTrimestralEnum(code).getDescription();
//	}
//
//	public String getMes(String code) {
//		return MensualEnum.getMensualEnum(code).getDescription();
//	}
//	
//	private boolean isNotNullMetaSelected(){
//		return metaSelected != null; 
//	}
}
