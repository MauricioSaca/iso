package com.org.indicadores.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.indicadores.model.Indicador;
import com.org.indicadores.model.Metas;
import com.org.indicadores.model.QMetas;
import com.org.indicadores.repository.MetasRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class MetasService extends BaseService<Metas, Long> {

	@Inject
	private MetasRepository metasRepository;

	@Override
	public BaseRepository<Metas, Long> getRepository() {
		return metasRepository;
	}
	
	public List<Metas> findMetasByIndicador(Indicador indicador){
		OrderSpecifier<String> order = QMetas.metas.ordinalDate.asc();
		BooleanExpression byIndicador = QMetas.metas.indicador.id.eq(indicador.getId());
		return (List<Metas>) metasRepository.findAll(byIndicador,order);
	}

	public boolean isNotExistMetaByOrdinariDate(String evaluationTypeCode) {
		BooleanExpression byOrdinariDate = QMetas.metas.ordinalDate.eq(evaluationTypeCode);
		return newJpaQuery().from(QMetas.metas).where(byOrdinariDate).notExists();
	}

	public boolean isHasNotMetas(Indicador indicador) {
		BooleanExpression byIndicador = QMetas.metas.indicador.id.eq(indicador.getId());
		return newJpaQuery().from(QMetas.metas).where(byIndicador).notExists();
	}

}
