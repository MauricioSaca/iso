package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Adopter;
import com.org.adoption.repository.AdopterRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class AdopterService extends BaseService<Adopter, Long>{

	@Inject
	private AdopterRepository AdopterRepository;
	
	@Override
	public BaseRepository<Adopter, Long> getRepository() {
		return AdopterRepository;
	}

}
