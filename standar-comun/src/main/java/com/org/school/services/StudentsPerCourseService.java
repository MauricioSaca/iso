package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.StudentsPerCourse;
import com.org.school.repository.StudentsPerCourseRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentsPerCourseService extends BaseService<StudentsPerCourse, Long>{

	@Inject
	private StudentsPerCourseRepository studentsPerCourseRepository;
	
	@Override
	public BaseRepository<StudentsPerCourse, Long> getRepository() {
		return studentsPerCourseRepository;
	}

}
