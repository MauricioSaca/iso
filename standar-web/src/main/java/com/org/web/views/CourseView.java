package com.org.web.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.enums.StudentLevel;
import com.org.school.model.Courses;
import com.org.school.model.Teacher;
import com.org.school.services.CoursesService;
import com.org.school.services.TeacherService;
import com.org.util.enumeration.ViewStatus;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class CourseView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 43568786343L;

	/** CDI INJECTION POINT */
	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient TeacherService teacherService;

	@Inject
	private FacesContext facesContext;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Courses, Long> lazyCourseModel;
	private Courses newCourse;
	private Courses courseSelected;

	private List<Teacher> teacherList;
	private Teacher teacherSelected;

	private List<StudentLevel> studentLevelList;
	private StudentLevel studentLevelSelected;

	@PostConstruct
	public void init() {
		loadLazyDataModels();
	}

	private void loadLazyDataModels() {
		lazyCourseModel = new BaseLazyModel<Courses, Long>(coursesService);
		teacherList = teacherService.findAll();
		studentLevelList = Arrays.asList(StudentLevel.values());
	}

	public void preparedCreatedCourse() {
		setStatus(ViewStatus.NEW);
		newCourse = new Courses();
	}

	public void preparedEditCourse() {
		setStatus(ViewStatus.EDIT);
	}

	public void saveCourse() {
		if (isNotNullNewCouse()) {
			if (isNotNullTeacher() && isNotNullLevel()) {
				newCourse.setStudentLevel(studentLevelSelected);
				newCourse.setTeacher(teacherSelected);
				coursesService.save(newCourse);
				Messages.create("Información").detail("Registro ingresado exitosamente").add();
			}
		}
	}

	public void updateCourse() {
		if (isNotNullCourseSelected()) {
			if (isNotNullTeacher() && isNotNullLevel()) {
				courseSelected.setStudentLevel(studentLevelSelected);
				courseSelected.setTeacher(teacherSelected);
				coursesService.save(courseSelected);
				Messages.create("Información").detail("Registro actualizada exitosamente").add();
			}
		}
	}

	public void deleteCourse() {
		if (isNotNullCourseSelected()) {
			coursesService.deleteOne(courseSelected);
			Messages.create("Información").detail("Registro eliminado exitosamente").add();
		}
	}

	private boolean isNotNullNewCouse() {
		return newCourse != null;
	}

	private boolean isNotNullCourseSelected() {
		return courseSelected != null;
	}

	private boolean isNotNullTeacher() {
		return teacherSelected != null;
	}
	
	private boolean isNotNullLevel() {
		return studentLevelSelected != null;
	}

}
