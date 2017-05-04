package com.org.school.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Courses;
import com.org.school.model.QStudentsPerCourse;
import com.org.school.model.StudentGradesPerSubject;
import com.org.school.model.StudentGradesPojo;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.SubjectPerCourse;
import com.org.school.repository.StudentsPerCourseRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentsPerCourseService extends BaseService<StudentsPerCourse, Long> {

	@Inject
	private StudentsPerCourseRepository studentsPerCourseRepository;
	
	private final static QStudentsPerCourse qStudentsPerCourse = QStudentsPerCourse.studentsPerCourse;

	@Override
	public BaseRepository<StudentsPerCourse, Long> getRepository() {
		return studentsPerCourseRepository;
	}

	public List<StudentsPerCourse> findStudentsListByCourse(Courses courses) {
		return newJpaQuery().from(qStudentsPerCourse).where(qStudentsPerCourse.courses.id.eq(courses.getId()))
				.list(qStudentsPerCourse);
	}

   
}
