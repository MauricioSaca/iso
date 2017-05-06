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
public class CalificacionesView implements Serializable {

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

	private transient Student student;

	private BaseLazyModel<Student, Long> studentLazyData;
	private boolean renderEditView;
	private User user;
	private List<Courses> courses;
	private List<StudentGradesPojo> studentsgrade;
	private StudentGradesPojo studentsgradeSelected;
	private List<StudentsPerCourse> studentsPerCourse;
	private Courses courseSelected;
	private List<SubjectPerCourse> subjectsperCourse;
	private SubjectPerCourse subjectperCourseSelected;
	private Teacher teacher;

	@PostConstruct
	public void init() {
		user = (User) identity.getAccount();
		teacher = teacherService.findTeacherByUser(user);
		courses = coursesService.findCoursesByTeacher(teacher);
	}

	public void onChangeCourse() {
		if(courseSelected != null){
			subjectsperCourse = subjectPerCourseService.findSubjectByCourse(courseSelected);			
		}
	}

	public void onChangeSubject() {

		if (subjectperCourseSelected != null) {
			studentsgrade = new ArrayList<>();
			StudentGradesPojo studentgradeElement = new StudentGradesPojo();
			StudentGradesPerSubject studentGrades;

			studentsPerCourse = studentsPerCourseService.findStudentsListByCourse(courseSelected);

			if (studentsPerCourse != null) {
				for (StudentsPerCourse studentsPerCourse : studentsPerCourse) {
					studentgradeElement = new StudentGradesPojo();
					studentgradeElement.setStudent(studentsPerCourse.getStudent());
					studentGrades = studentGradesPerSubjectService
							.findbyStudentAndSubject(studentsPerCourse.getStudent(), subjectperCourseSelected);
					if (studentGrades != null) {
						studentgradeElement.setNota1(studentGrades.getFirstGrade());
						studentgradeElement.setNota2(studentGrades.getSecondGrade());
						studentgradeElement.setNota3(studentGrades.getThirdGrade());
					}
					studentgradeElement.setSubjectPerCourse(subjectperCourseSelected);
					studentsgrade.add(studentgradeElement);
				}

			}
		} else {
			studentsgrade = new ArrayList<>();
		}

	}

	public void onCellEdit(CellEditEvent event) {
		Double newValue = (Double) event.getNewValue();
		Double oldValue = (Double) event.getOldValue();
		studentsgradeSelected = (StudentGradesPojo) ((DataTable) event.getComponent()).getRowData();

		if (newValue.compareTo(oldValue) != 0) {
			if (newValue > 10 || newValue < 0) {
				Messages.create("ERROR").detail("Las notas deben ser del 0 al 10").error().add();
				newValue = oldValue;
			} else {
				StudentGradesPerSubject studentGradesPerSubject;
				studentGradesPerSubject = studentGradesPerSubjectService
						.findbyStudentAndSubject(studentsgradeSelected.getStudent(), subjectperCourseSelected);
				if (studentGradesPerSubject == null) {
					studentGradesPerSubject = new StudentGradesPerSubject();
					studentGradesPerSubject.setStudent(studentsgradeSelected.getStudent());
					studentGradesPerSubject.setSubjectPerCourse(studentsgradeSelected.getSubjectPerCourse());
				}
				studentGradesPerSubject.setFirstGrade(studentsgradeSelected.getNota1());
				studentGradesPerSubject.setSecondGrade(studentsgradeSelected.getNota2());
				studentGradesPerSubject.setThirdGrade(studentsgradeSelected.getNota3());

				studentGradesPerSubjectService.save(studentGradesPerSubject);

				Messages.create("INFO").detail("Nota Guardada exitosamente").add();
			}
		}

	}
}
