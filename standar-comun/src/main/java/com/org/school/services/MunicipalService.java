package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Municipal;
import com.org.school.repository.MunicipalRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class MunicipalService extends BaseService<Municipal, Long>{

	@Inject
	private MunicipalRepository municipalRepository;
	
	
	@Override
	public BaseRepository<Municipal, Long> getRepository() {
		return municipalRepository;
	}

}
