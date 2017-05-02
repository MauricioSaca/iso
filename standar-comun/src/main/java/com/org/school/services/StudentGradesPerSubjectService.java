package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.StudentGradesPerSubject;
import com.org.school.repository.StudentGradesPerSubjectRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentGradesPerSubjectService extends BaseService<StudentGradesPerSubject, Long>{

	@Inject
	private StudentGradesPerSubjectRepository studentGradesPerSubjectRepository;
	
	@Override
	public BaseRepository<StudentGradesPerSubject, Long> getRepository() {
		return studentGradesPerSubjectRepository;
	}

}
