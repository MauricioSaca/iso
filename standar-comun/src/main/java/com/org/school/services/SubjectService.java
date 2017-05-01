package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Subject;
import com.org.school.repository.SubjectRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class SubjectService extends BaseService<Subject, Long>{

	@Inject
	private SubjectRepository subjectRepository;
	
	@Override
	public BaseRepository<Subject, Long> getRepository() {
		return subjectRepository;
	}

}
