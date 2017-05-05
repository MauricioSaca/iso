package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.QStudentCourseAttendance;
import com.org.school.model.StudentCourseAttendance;
import com.org.school.repository.StudentCourseAttendanceRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentCourseAttendanceService extends BaseService<StudentCourseAttendance, Long> {

	@Inject
	private StudentCourseAttendanceRepository studentCourseAttendanceRepository;

	private final static QStudentCourseAttendance qStudentCourseAttendance = QStudentCourseAttendance.studentCourseAttendance;

	@Override
	public BaseRepository<StudentCourseAttendance, Long> getRepository() {
		return studentCourseAttendanceRepository;
	}

	public StudentCourseAttendance findByStudents(Long id) {
		return newJpaQuery().from(qStudentCourseAttendance).where(qStudentCourseAttendance.studentsPerCourse.id.eq(id))
				.singleResult(qStudentCourseAttendance);
	}
	
	public List<StudentCourseAttendance> findListByStudents(Long id) {
		return newJpaQuery().from(qStudentCourseAttendance).where(qStudentCourseAttendance.studentsPerCourse.id.eq(id))
				.list(qStudentCourseAttendance);
	}

}
