package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.QTeacher;
import com.org.school.model.Teacher;
import com.org.school.repository.TeacherRepository;
import com.org.security.identity.stereotype.User;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class TeacherService extends BaseService<Teacher, Long> {

	@Inject
	private TeacherRepository teacherRepository;

	private final static QTeacher qteacher = QTeacher.teacher;

	@Override
	public BaseRepository<Teacher, Long> getRepository() {
		return teacherRepository;
	}

	public Teacher findTeacherByUser(User user) {
		BooleanExpression byUser = qteacher.userTypeEntity.userName.eq(user.getUserName());
		return newJpaQuery().from(qteacher).where(byUser).singleResult(qteacher);
	}

}
