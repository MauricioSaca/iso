package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Courses;
import com.org.school.model.QSubjectPerCourse;
import com.org.school.model.Subject;
import com.org.school.model.SubjectPerCourse;
import com.org.school.repository.SubjectPerCourseRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class SubjectPerCourseService extends BaseService<SubjectPerCourse, Long> {

	@Inject
	private SubjectPerCourseRepository subjectPerCourseRepository;

	private final static QSubjectPerCourse qSubjectPerCourse = QSubjectPerCourse.subjectPerCourse;

	@Override
	public BaseRepository<SubjectPerCourse, Long> getRepository() {
		return subjectPerCourseRepository;
	}
	
	public List<Subject> findListSubjectByCourse(Courses course) {
		BooleanExpression byCourse= qSubjectPerCourse.courses.eq(course);
		return newJpaQuery().from(qSubjectPerCourse).where(byCourse).list(qSubjectPerCourse.subject);
	}

	public List<SubjectPerCourse> findSubjectByCourse(Courses courses) {
		return newJpaQuery().from(qSubjectPerCourse).where(qSubjectPerCourse.courses.id.eq(courses.getId()))
				.list(qSubjectPerCourse);
	}

}
