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

import com.org.school.model.Student;
import com.org.school.model.StudentCourseAttendance;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.Teacher;
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
	private transient Identity identity;
	
	private transient Teacher teacher;
	private transient User user;
	private BaseLazyModel<Teacher, Long> teacherLazyData;
	private boolean renderEditView;
	
	private List<StudentsPerCourse> studentsList;
	
	@PostConstruct
	public void init(){
		renderEditView = false;
		user = (User) identity.getAccount();
		teacher = getTeacherService().findTeacherByUser(user);
		
		//Cargar el listado de alumnos del curso del profesor
		studentsList = getStudentsPerCourseService().findStudentsListByCourse(teacher.getCourses());
		
		//mostrar en pantalla y guardar la asistencia
	}
	
	public void saveAsistencia(){
		List<StudentCourseAttendance> listToSave = new ArrayList<>();
		StudentCourseAttendance studentCourseAttendance;
		
		for(StudentsPerCourse studentsPerCourse : studentsList){
			
			studentCourseAttendance = new StudentCourseAttendance();
			
			studentCourseAttendance.setStudentsPerCourse(studentsPerCourse);
			studentCourseAttendance.setAttendance(studentsPerCourse.getAttendance());
			studentCourseAttendance.setDateAttendance(new Date());
			
			listToSave.add(studentCourseAttendance);
		}
		
		getStudentCourseAttendanceService().save(listToSave);
		
		Messages.create("Asistencia").detail("Asistencia de alumnos almacenada").add();
	}
	
}
