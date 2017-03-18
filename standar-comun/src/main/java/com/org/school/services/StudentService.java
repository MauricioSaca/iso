package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Student;
import com.org.school.repository.StudentRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentService extends BaseService<Student, Long>{

	@Inject
	private StudentRepository studentRepository;
	
	@Override
	public BaseRepository<Student, Long> getRepository() {
		return studentRepository;
	}

}
