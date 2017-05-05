package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.enums.StudentLevel;
import com.org.school.model.Courses;
import com.org.school.model.QCourses;
import com.org.school.model.Teacher;
import com.org.school.repository.CoursesRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class CoursesService extends BaseService<Courses, Long>{

	@Inject
	private CoursesRepository coursesRepository;
	
	private final static QCourses qCourses = QCourses.courses;
	
	@Override
	public BaseRepository<Courses, Long> getRepository() {
		return coursesRepository;
	}
	
	public List<Courses> findCoursesByTeacher(Teacher teacher) {
		return newJpaQuery().from(qCourses).where(qCourses.teacher.id.eq(teacher.getId()))
				.list(qCourses);
	}
	
	public List<Courses> findCoursesByLevel(StudentLevel level) {
		return newJpaQuery().from(qCourses).where(qCourses.studentLevel.eq(level.getCode()))
				.list(qCourses);
	}

}
