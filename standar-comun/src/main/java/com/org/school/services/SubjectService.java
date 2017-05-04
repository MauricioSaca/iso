package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Courses;
import com.org.school.model.QSubject;
import com.org.school.model.Subject;
import com.org.school.repository.SubjectRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class SubjectService extends BaseService<Subject, Long>{

	@Inject
	private SubjectRepository subjectRepository;
	
	private final static QSubject qsubject = QSubject.subject;
	
	@Override
	public BaseRepository<Subject, Long> getRepository() {
		return subjectRepository;
	}
	
}
