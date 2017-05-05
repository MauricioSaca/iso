package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.picketlink.Identity;

import com.org.school.model.BehaviourMonitoring;
import com.org.school.model.Courses;
import com.org.school.model.Student;
import com.org.school.model.StudentCourseAttendance;
import com.org.school.model.StudentGradesPerSubject;
import com.org.school.model.StudentGradesPojo;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.SubjectPerCourse;
import com.org.school.services.BehaviourMonitoringService;
import com.org.school.services.CoursesService;
import com.org.school.services.StudentCourseAttendanceService;
import com.org.school.services.StudentGradesPerSubjectService;
import com.org.school.services.StudentService;
import com.org.school.services.StudentsPerCourseService;
import com.org.school.services.SubjectPerCourseService;
import com.org.school.services.TeacherService;

import com.org.security.identity.stereotype.User;
import com.org.security.service.SecurityManagedService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ConsultaAsistenciaView implements Serializable {

	private static final long serialVersionUID = 80353393876789L;

	@Inject
	private transient SecurityManagedService securityManagedService;

	@Inject
	private transient Identity identity;

	@Inject
	private transient StudentService studentService;

	@Inject
	private transient TeacherService teacherService;

	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient SubjectPerCourseService subjectPerCourseService;

	@Inject
	private transient StudentCourseAttendanceService studentCourseAttendanceService;
	
	@Inject
	private transient BehaviourMonitoringService behaviourMonitoringService;

	@Inject
	private transient StudentsPerCourseService studentsPerCourseService;

	private BaseLazyModel<Student, Long> studentLazyData;
	private boolean renderEditView;
	private User user;
	private List<StudentGradesPojo> studentsgrade;
	private List<StudentCourseAttendance> studentCourseAttendanceList;
	private List<BehaviourMonitoring> behaviourMonitoringList;
	private StudentGradesPerSubject studentGradesPerSubject;
	private StudentGradesPojo studentsgradeSelected;
	private StudentsPerCourse studentPerCourse;
	private Courses course;
	private List<SubjectPerCourse> subjectsperCourse;
	private SubjectPerCourse subjectperCourseSelected;
	private Student student;

	@PostConstruct
	public void init() {
		studentCourseAttendanceList = new ArrayList<>();
		user = (User) identity.getAccount();
		student = studentService.findStudentByUser(user);
		studentPerCourse = studentsPerCourseService.findByStudent(student);
		studentCourseAttendanceList = studentCourseAttendanceService.findListByStudents(studentPerCourse.getId());
		behaviourMonitoringList = behaviourMonitoringService.findBehaviourMonitoringByStudent(studentPerCourse);		
	}


}
