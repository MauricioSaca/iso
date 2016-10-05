package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Cat;
import com.org.adoption.repository.CatRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class CatService extends BaseService<Cat, Long>{

	@Inject
	private CatRepository CatRepository;
	
	@Override
	public BaseRepository<Cat, Long> getRepository() {
		return CatRepository;
	}

}
