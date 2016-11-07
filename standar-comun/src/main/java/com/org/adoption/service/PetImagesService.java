package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.PetImages;
import com.org.adoption.repository.PetImagesRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PetImagesService extends BaseService<PetImages, Long>{

	@Inject
	private PetImagesRepository petImagesRepository;
	
	@Override
	public BaseRepository<PetImages, Long> getRepository() {
		return petImagesRepository;
	}

}
