package com.org.indicadores.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.indicadores.model.Indicador;
import com.org.indicadores.repository.IndicadorRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class IndicadorService extends BaseService<Indicador, Long>{

	@Inject
	private IndicadorRepository indicadorRepository;
	
	@Override
	public BaseRepository<Indicador, Long> getRepository() {
		return indicadorRepository;
	}
	
}
