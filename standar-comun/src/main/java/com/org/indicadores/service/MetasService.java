package com.org.indicadores.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.indicadores.model.Metas;
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

}
