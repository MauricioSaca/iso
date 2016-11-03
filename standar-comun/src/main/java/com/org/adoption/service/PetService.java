package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Pet;
import com.org.adoption.repository.PetRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PetService extends BaseService<Pet, Long>{

	@Inject
	private PetRepository petRepository;
	
	@Override
	public BaseRepository<Pet, Long> getRepository() {
		return petRepository;
	}

}
