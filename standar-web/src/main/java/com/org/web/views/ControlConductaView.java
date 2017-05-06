package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.google.common.base.Strings;
import com.org.school.model.Assigments;
import com.org.school.model.BehaviourMonitoring;
import com.org.school.model.CatalogoCodigos;
import com.org.school.model.Courses;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.SubjectPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.AssigmentsService;
import com.org.school.services.BehaviourMonitoringService;
import com.org.school.services.CatalogoCodigosService;
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
public class ControlConductaView implements Serializable {

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
	private transient BehaviourMonitoringService behaviourMonitoringService;
	
	@Inject
	private transient CatalogoCodigosService catalogoCodigosService;

	@Inject
	private transient Identity identity;

	private transient Teacher teacher;
	private transient User user;
	private transient Courses selectedCourse;
	private transient BehaviourMonitoring selectedBehaviourMonitoring;
	private transient StudentsPerCourse selectedStudentsPerCourse;
	private BaseLazyModel<BehaviourMonitoring, Long> behaviourMonitoringLazyData;
	private String selectedTipoCodigo;
	private List<SubjectPerCourse> subjectPerCoursesList;
	private List<Courses> coursesList;
	private List<StudentsPerCourse> studentsList;
	private List<BehaviourMonitoring> behaviourMonitoringsList;
	private List<CatalogoCodigos> codesList;
	private List<CatalogoCodigos> selectedCodesList;

	@PostConstruct
	public void init() {

		selectedBehaviourMonitoring = new BehaviourMonitoring();

		user = (User) identity.getAccount();
		teacher = getTeacherService().findTeacherByUser(user);

		coursesList = getCoursesService().findCoursesByTeacher(teacher);

	}

	public void loadAssigments() {
		behaviourMonitoringLazyData = new BaseLazyModel<>(getBehaviourMonitoringService());
		behaviourMonitoringLazyData.setCustomFilters(buildWhere());
	}

	public Set<ValueHolder> buildWhere() {
		Set<ValueHolder> holder = new HashSet<>();

		if (selectedCourse != null) {
			holder.add(new ValueHolder("subjectPerCourse.courses.id", selectedCourse.getId()));
		}

		return holder;
	}

	public void loadCodigos() {
		if(!Strings.isNullOrEmpty(selectedTipoCodigo)){
			codesList = getCatalogoCodigosService().findCatalogoByTipo(selectedTipoCodigo);		
		}
	}
	
	public void loadStudents() {
		if(selectedCourse != null){
			studentsList = getStudentsPerCourseService().findStudentsListByCourse(selectedCourse);		
		}
	}
	
	public void getCodigosStudent(StudentsPerCourse studentsPerCourse){
		behaviourMonitoringsList = getBehaviourMonitoringService().findBehaviourMonitoringByStudent(studentsPerCourse);
	}

	public void save() {
		
		for (int i = 0; i < selectedCodesList.size() ; i++) {
	
			selectedBehaviourMonitoring = new BehaviourMonitoring();
			selectedBehaviourMonitoring.setCatalogoCodigos(selectedCodesList.get(i));
			selectedBehaviourMonitoring.setStudentsPerCourse(selectedStudentsPerCourse);
			selectedBehaviourMonitoring.setFechaAplicado(new Date());
			
			getBehaviourMonitoringService().save(selectedBehaviourMonitoring);
			
		}
		
		Messages.create("Asignacion").detail("Codigo(s) ingresado(s) correctamente").add();

		selectedBehaviourMonitoring = new BehaviourMonitoring();
	}

}
