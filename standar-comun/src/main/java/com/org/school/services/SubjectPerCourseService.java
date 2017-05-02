package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.SubjectPerCourse;
import com.org.school.repository.SubjectPerCourseRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class SubjectPerCourseService extends BaseService<SubjectPerCourse, Long>{

	@Inject
	private SubjectPerCourseRepository subjectPerCourseRepository;
	
	@Override
	public BaseRepository<SubjectPerCourse, Long> getRepository() {
		return subjectPerCourseRepository;
	}

}
