package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Teacher;
import com.org.school.repository.TeacherRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class TeacherService extends BaseService<Teacher, Long> {

	@Inject
	private TeacherRepository teacherRepository;
	
	@Override
	public BaseRepository<Teacher, Long> getRepository() {
		return teacherRepository;
	}

}
