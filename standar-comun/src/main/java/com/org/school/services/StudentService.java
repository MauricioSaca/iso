package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Student;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.QStudent;
import com.org.school.repository.StudentRepository;
import com.org.security.identity.stereotype.User;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentService extends BaseService<Student, Long>{

	@Inject
	private StudentRepository studentRepository;
	
	private final static QStudent qstudent = QStudent.student;

	
	@Override
	public BaseRepository<Student, Long> getRepository() {
		return studentRepository;
	}
	
	public Student findStudentByUser(User user) {
		BooleanExpression byUser = qstudent.userTypeEntity.userName.eq(user.getUserName());
		return newJpaQuery().from(qstudent).where(byUser).singleResult(qstudent);
	}

}
