package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.school.model.Courses;
import com.org.school.model.StudentCourseAttendance;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.CoursesService;
import com.org.school.services.StudentCourseAttendanceService;
import com.org.school.services.StudentsPerCourseService;
import com.org.school.services.TeacherService;
import com.org.security.identity.stereotype.User;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class AsistenciaView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private transient StudentCourseAttendanceService studentCourseAttendanceService;

	@Inject
	private transient TeacherService teacherService;

	@Inject
	private transient StudentsPerCourseService studentsPerCourseService;

	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient Identity identity;

	private transient Teacher teacher;
	private transient User user;
	private transient Courses selectedCourse;
	private BaseLazyModel<Teacher, Long> teacherLazyData;
	private boolean renderEditView;

	private List<StudentsPerCourse> studentsList;
	private List<Courses> coursesList;
	private List<StudentCourseAttendance> studentCourseAttendancesList;

	@PostConstruct
	public void init() {
		renderEditView = false;
		user = (User) identity.getAccount();
		teacher = getTeacherService().findTeacherByUser(user);

		coursesList = getCoursesService().findCoursesByTeacher(teacher);

	}

	public void loadStudents() {
		studentCourseAttendancesList = new ArrayList<>();
		if (selectedCourse != null) {
			studentsList = getStudentsPerCourseService().findStudentsListByCourse(selectedCourse);

			makePojo();
		}
	}

	public void makePojo() {
		for (StudentsPerCourse student : studentsList) {
			StudentCourseAttendance studentCourseAttendance = getStudentCourseAttendanceService()
					.findByStudents(student.getId());

			if (studentCourseAttendance == null) {
				studentCourseAttendance = new StudentCourseAttendance();
				studentCourseAttendance.setAttendance(false);
				studentCourseAttendance.setStudentsPerCourse(student);

			}

			studentCourseAttendancesList.add(studentCourseAttendance);
		}
	}

	public void prepareSave() {
		renderEditView = true;
		teacher = new Teacher();
	}

	public void saveAsistencia() {
		List<StudentCourseAttendance> listToSave = new ArrayList<>();

		for (StudentCourseAttendance attendance : studentCourseAttendancesList) {

			attendance.setDateAttendance(new Date());

			listToSave.add(attendance);
		}

		getStudentCourseAttendanceService().save(listToSave);

		Messages.create("Asistencia").detail("Asistencia de alumnos almacenada").add();
	}

}
