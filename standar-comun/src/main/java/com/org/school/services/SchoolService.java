package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.School;
import com.org.school.repository.SchoolRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class SchoolService extends BaseService<School, Long> {

	@Inject
	private SchoolRepository schoolRepository;

	@Override
	public BaseRepository<School, Long> getRepository() {
		return schoolRepository;
	}

}
