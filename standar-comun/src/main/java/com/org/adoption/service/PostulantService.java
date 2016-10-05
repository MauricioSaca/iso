package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Postulant;
import com.org.adoption.repository.PostulantRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PostulantService extends BaseService<Postulant, Long>{

	
	@Inject
	private PostulantRepository postulantRepository;
	
	@Override
	public BaseRepository<Postulant, Long> getRepository() {
		return postulantRepository;
	}

}
