package com.org.web.views;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;
import org.picketlink.idm.credential.Password;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import com.org.school.enums.Gender;
import com.org.school.enums.Shift;
import com.org.school.model.Courses;
import com.org.school.model.Student;
import com.org.school.model.StudentGradesPerSubject;
import com.org.school.model.StudentGradesPojo;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.Subject;
import com.org.school.model.SubjectPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.CoursesService;
import com.org.school.services.StudentGradesPerSubjectService;
import com.org.school.services.StudentService;
import com.org.school.services.StudentsPerCourseService;
import com.org.school.services.SubjectPerCourseService;
import com.org.school.services.SubjectService;
import com.org.school.services.TeacherService;
import com.org.security.enums.GroupsSecurityRolesNames;
import com.org.security.enums.RolesSecurityNames;
import com.org.security.identity.model.UserTypeEntity;
import com.org.security.identity.stereotype.Group;
import com.org.security.identity.stereotype.Role;
import com.org.security.identity.stereotype.User;
import com.org.security.service.SecurityManagedService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ConsultaNotasView implements Serializable {

	private static final long serialVersionUID = 80351893876789L;

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
	private transient StudentGradesPerSubjectService studentGradesPerSubjectService;

	@Inject
	private transient StudentsPerCourseService studentsPerCourseService;

	private BaseLazyModel<Student, Long> studentLazyData;
	private boolean renderEditView;
	private User user;
	private List<StudentGradesPojo> studentsgrade;
	private List<StudentGradesPerSubject> studentGradesPerSubjectList;
	private StudentGradesPerSubject studentGradesPerSubject;
	private StudentGradesPojo studentsgradeSelected;
	private List<StudentsPerCourse> studentsPerCourse;
	private Courses course;
	private List<SubjectPerCourse> subjectsperCourse;
	private SubjectPerCourse subjectperCourseSelected;
	private Student student;

	@PostConstruct
	public void init() {
		studentGradesPerSubjectList = new ArrayList<>();
		user = (User) identity.getAccount();
		student = studentService.findStudentByUser(user);
		course = studentsPerCourseService.findCoursesByStudent(student);
		subjectsperCourse = subjectPerCourseService.findSubjectByCourse(course);
		for (SubjectPerCourse subjectPerCourse : subjectsperCourse) {
			studentGradesPerSubject = studentGradesPerSubjectService.findbyStudentAndSubject(student, subjectPerCourse);
			if(studentGradesPerSubject ==  null){
				studentGradesPerSubject = new StudentGradesPerSubject();
				studentGradesPerSubject.setStudent(student);
				studentGradesPerSubject.setSubjectPerCourse(subjectPerCourse);
			}
			studentGradesPerSubjectList.add(studentGradesPerSubject);
		}
	}


}
