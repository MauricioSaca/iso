package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Courses;
import com.org.school.repository.CoursesRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class CoursesService extends BaseService<Courses, Long>{

	@Inject
	private CoursesRepository coursesRepository;
	
	@Override
	public BaseRepository<Courses, Long> getRepository() {
		return coursesRepository;
	}

}
