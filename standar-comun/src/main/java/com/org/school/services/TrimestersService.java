package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Trimesters;
import com.org.school.repository.TrimestersRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class TrimestersService extends BaseService<Trimesters, Long> {

	@Inject
	private TrimestersRepository trimestersRepository;

	@Override
	public BaseRepository<Trimesters, Long> getRepository() {
		return trimestersRepository;
	}

}
