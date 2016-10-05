package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Dog;
import com.org.adoption.repository.DogRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class DogService extends BaseService<Dog, Long>{
	
	@Inject
	private DogRepository dogRepository;

	@Override
	public BaseRepository<Dog, Long> getRepository() {
		return dogRepository;
	}

}
