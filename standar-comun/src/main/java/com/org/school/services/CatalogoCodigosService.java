package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.CatalogoCodigos;
import com.org.school.model.QCatalogoCodigos;
import com.org.school.repository.CatalogoCodigosRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class CatalogoCodigosService extends BaseService<CatalogoCodigos, Long> {

	@Inject
	private CatalogoCodigosRepository catalogoCodigosRepository;

	private final static QCatalogoCodigos qCatalogoCodigos = QCatalogoCodigos.catalogoCodigos;

	@Override
	public BaseRepository<CatalogoCodigos, Long> getRepository() {
		return catalogoCodigosRepository;
	}

	public List<CatalogoCodigos> findCatalogoByTipo(String tipo) {
		return newJpaQuery().from(qCatalogoCodigos).where(qCatalogoCodigos.tipo.eq(tipo)).list(qCatalogoCodigos);
	}

}
