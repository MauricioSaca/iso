package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.AdoptedPets;
import com.org.adoption.repository.AdoptedPetsRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class AdoptedPetsService extends BaseService<AdoptedPets, Long>{

	
	@Inject
	private AdoptedPetsRepository adoptedPetsRepository;
	
	@Override
	public BaseRepository<AdoptedPets, Long> getRepository() {
		return adoptedPetsRepository;
	}

}
