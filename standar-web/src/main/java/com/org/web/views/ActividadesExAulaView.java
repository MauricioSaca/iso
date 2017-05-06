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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.org.school.model.Assigments;
import com.org.school.model.Courses;
import com.org.school.model.EntregaTareas;
import com.org.school.model.SubjectPerCourse;
import com.org.school.model.Teacher;
import com.org.school.services.AssigmentsService;
import com.org.school.services.CoursesService;
import com.org.school.services.EntregaTareasService;
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
	private transient EntregaTareasService entregaTareasService;

	@Inject
	private transient Identity identity;

	private transient Teacher teacher;
	private transient User user;
	private transient Courses selectedCourse;
	private transient Assigments selectedAssigments;
	private BaseLazyModel<Assigments, Long> assigmentsLazyData;
	private BaseLazyModel<EntregaTareas, Long> entregaTareasLazyData;
	private SubjectPerCourse selectedSubjectPerCourse;
	private List<SubjectPerCourse> subjectPerCoursesList;
	private List<Courses> coursesList;

	private StreamedContent file;

	@PostConstruct
	public void init() {

		selectedAssigments = new Assigments();

		user = (User) identity.getAccount();
		teacher = getTeacherService().findTeacherByUser(user);

		coursesList = getCoursesService().findCoursesByTeacher(teacher);

	}

	public void loadAssigments() {
		assigmentsLazyData = new BaseLazyModel<>(getAssigmentsService());
		assigmentsLazyData.setCustomFilters(buildWhere());
	}

	public Set<ValueHolder> buildWhere() {
		Set<ValueHolder> holder = new HashSet<>();

		if (selectedCourse != null) {
			holder.add(new ValueHolder("subjectPerCourse.courses.id", selectedCourse.getId()));
		}

		return holder;
	}

	public void loadEntregas() {
		entregaTareasLazyData = new BaseLazyModel<>(getEntregaTareasService());
		entregaTareasLazyData.setCustomFilters(buildWhereDownload());
	}

	public Set<ValueHolder> buildWhereDownload() {
		Set<ValueHolder> holder = new HashSet<>();

		if (selectedCourse != null && selectedSubjectPerCourse != null) {
			holder.add(new ValueHolder("studentsPerCourse.courses.id", selectedCourse.getId()));
			holder.add(new ValueHolder("assigments.subjectPerCourse.id", selectedSubjectPerCourse.getId()));
		}

		return holder;
	}

	public void loadSubjects() {
		if (selectedCourse != null) {
			subjectPerCoursesList = getSubjectPerCourseService().findSubjectByCourse(selectedCourse);
		}
	}

	public void prepareCreate() {
		selectedAssigments = new Assigments();
	}

	public void save() {
		getAssigmentsService().save(selectedAssigments);
		Messages.create("Asignacion").detail("La tarea ex-aula ha sido almacenada").add();

		selectedAssigments = new Assigments();
	}

	public void prepareEdit(Assigments assigments) {
		selectedAssigments = assigments;
	}

	public void remove(Assigments assigments) {
		if (assigments != null) {
			getAssigmentsService().delete(assigments);
			Messages.create("Asignacion eliminada").detail("Tarea ex-aula eliminada").add();
		}
	}

	public void download(EntregaTareas tarea) {
		file = new DefaultStreamedContent(new ByteArrayInputStream(tarea.getFile()), "application/pdf",
				tarea.getFileName() + ".pdf");
	}

}
