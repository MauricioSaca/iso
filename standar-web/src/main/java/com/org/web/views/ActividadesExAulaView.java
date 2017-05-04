package com.org.web.views;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.school.model.Assigments;
import com.org.school.model.Courses;
import com.org.school.model.SubjectPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.AssigmentsService;
import com.org.school.services.CoursesService;
import com.org.school.services.StudentCourseAttendanceService;
import com.org.school.services.StudentsPerCourseService;
import com.org.school.services.SubjectPerCourseService;
import com.org.school.services.TeacherService;
import com.org.security.identity.stereotype.User;
import com.org.util.safe.ValueHolder;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ActividadesExAulaView implements Serializable {

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
	private transient SubjectPerCourseService subjectPerCourseService;

	@Inject
	private transient AssigmentsService assigmentsService;

	@Inject
	private transient Identity identity;

	private transient Teacher teacher;
	private transient User user;
	private transient Courses selectedCourse;
	private transient Assigments selectedAssigments;
	private BaseLazyModel<Assigments, Long> assigmentsLazyData;

	private List<SubjectPerCourse> subjectPerCoursesList;
	private List<Courses> coursesList;

	@PostConstruct
	public void init() {
		
		selectedAssigments = new Assigments();

		user = (User) identity.getAccount();
		teacher = getTeacherService().findTeacherByUser(user);

		coursesList = getCoursesService().findCoursesByTeacher(teacher);

	}
	
	public void loadAssigments(){
		assigmentsLazyData = new BaseLazyModel<>(getAssigmentsService());
		assigmentsLazyData.setCustomFilters(buildWhere());
	}
	
	public Set<ValueHolder> buildWhere(){
		Set<ValueHolder> holder = new HashSet<>();

		if(selectedCourse != null){ 
			holder.add(new ValueHolder("subjectPerCourse.courses.id", selectedCourse.getId()));			
		}
		
		return holder;
	}
	

	public void loadSubjects() {
		if (selectedCourse != null) {
			subjectPerCoursesList = getSubjectPerCourseService().findSubjectByCourse(selectedCourse);
			loadAssigments();
		}
	}

	public void save() {
		getAssigmentsService().save(selectedAssigments);
		Messages.create("Asignacion").detail("La tarea ex-aula ha sido almacenada").add();
		
		selectedAssigments = new Assigments();
	}

}
