package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.StudentCourseAttendance;
import com.org.school.repository.StudentCourseAttendanceRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentCourseAttendanceService extends BaseService<StudentCourseAttendance, Long>{

	@Inject
	private StudentCourseAttendanceRepository studentCourseAttendanceRepository;
	
	@Override
	public BaseRepository<StudentCourseAttendance, Long> getRepository() {
		return studentCourseAttendanceRepository;
	}

}
