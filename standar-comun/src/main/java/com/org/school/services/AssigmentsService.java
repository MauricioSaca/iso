package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Assigments;
import com.org.school.repository.AssigmentsRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class AssigmentsService extends BaseService<Assigments, Long>{

	@Inject
	private AssigmentsRepository assigmentsRepository;
	
	@Override
	public BaseRepository<Assigments, Long> getRepository() {
		return assigmentsRepository;
	}

}
