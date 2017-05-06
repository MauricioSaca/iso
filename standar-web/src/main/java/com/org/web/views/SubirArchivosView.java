package com.org.web.views;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;
import org.primefaces.event.FileUploadEvent;

import com.org.school.model.Assigments;
import com.org.school.model.Courses;
import com.org.school.model.EntregaTareas;
import com.org.school.model.Student;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.SubjectPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.AssigmentsService;
import com.org.school.services.CoursesService;
import com.org.school.services.EntregaTareasService;
import com.org.school.services.StudentCourseAttendanceService;
import com.org.school.services.StudentService;
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
public class SubirArchivosView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private transient StudentCourseAttendanceService studentCourseAttendanceService;

	@Inject
	private transient StudentService studentService;

	@Inject
	private transient StudentsPerCourseService studentsPerCourseService;

	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient SubjectPerCourseService subjectPerCourseService;

	@Inject
	private transient AssigmentsService assigmentsService;

	@Inject
	private transient EntregaTareasService entregaTareasService;

	@Inject
	private transient Identity identity;

	private transient Student student;
	private transient User user;
	private transient StudentsPerCourse selectedStudentsPerCourse;
	private transient SubjectPerCourse selectedSubjectPerCourse;
	private transient EntregaTareas selectedEntregaTareas;
	private byte[] file;
	private String nameFile;
	private transient Assigments selectedAssigments;
	private BaseLazyModel<Assigments, Long> assigmentsLazyData;
	private boolean showSave = false;
	private List<SubjectPerCourse> subjectPerCoursesList;

	@PostConstruct
	public void init() {

		selectedAssigments = new Assigments();

		user = (User) identity.getAccount();
		student = getStudentService().findStudentByUser(user);

		selectedStudentsPerCourse = getStudentsPerCourseService().findByStudent(student);

		subjectPerCoursesList = getSubjectPerCourseService()
				.findSubjectByCourse(selectedStudentsPerCourse.getCourses());

	}

	public void loadAssigments() {
		assigmentsLazyData = new BaseLazyModel<>(getAssigmentsService());
		assigmentsLazyData.setCustomFilters(buildWhere());
	}

	public Set<ValueHolder> buildWhere() {
		Set<ValueHolder> holder = new HashSet<>();

		if (selectedSubjectPerCourse != null) {
			holder.add(new ValueHolder("subjectPerCourse.subject.id", selectedSubjectPerCourse.getId()));
		}

		return holder;
	}

	public void uploadFile(FileUploadEvent event) {
		file = event.getFile().getContents();
		nameFile = event.getFile().getFileName();
		save();
	}

	public void save() {

		selectedEntregaTareas = getEntregaTareasService().findEntregaTareas(selectedStudentsPerCourse,
				selectedAssigments);

		if(selectedEntregaTareas == null){
			selectedEntregaTareas = new EntregaTareas();
			selectedEntregaTareas.setFechaEntrega(new Date());
			selectedEntregaTareas.setAssigments(selectedAssigments);
			selectedEntregaTareas.setStudentsPerCourse(selectedStudentsPerCourse);			
		}

		selectedEntregaTareas.setFile(file);
		selectedEntregaTareas.setFileName(nameFile);
		getEntregaTareasService().save(selectedEntregaTareas);

		Messages.create("Tarea").detail("Subida correctamente").add();
	}
}
