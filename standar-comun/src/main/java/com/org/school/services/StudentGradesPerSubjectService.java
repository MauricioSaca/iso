package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Student;
import com.org.school.model.StudentGradesPerSubject;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.QStudentGradesPerSubject;
import com.org.school.model.SubjectPerCourse;
import com.org.school.repository.StudentGradesPerSubjectRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class StudentGradesPerSubjectService extends BaseService<StudentGradesPerSubject, Long>{

	@Inject
	private StudentGradesPerSubjectRepository studentGradesPerSubjectRepository;
	
	private final static QStudentGradesPerSubject qStudentGradesPerSubject = QStudentGradesPerSubject.studentGradesPerSubject;
	
	@Override
	public BaseRepository<StudentGradesPerSubject, Long> getRepository() {
		return studentGradesPerSubjectRepository;
	}
	
	public StudentGradesPerSubject findbyStudentAndSubject(Student student, SubjectPerCourse spc){
		BooleanExpression byStudent = qStudentGradesPerSubject.student.eq(student);
		BooleanExpression bySubject = qStudentGradesPerSubject.subjectPerCourse.eq(spc);
		return newJpaQuery().from(qStudentGradesPerSubject).where(byStudent.and(bySubject)).singleResult(qStudentGradesPerSubject);
	}
	
	public List<StudentGradesPerSubject> findbyStudent(Student student){
		BooleanExpression byStudent = qStudentGradesPerSubject.student.eq(student);
		return newJpaQuery().from(qStudentGradesPerSubject).where(byStudent).list(qStudentGradesPerSubject);
	}

}
