package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.school.model.Courses;
import com.org.school.model.Period;
import com.org.school.model.Subject;
import com.org.school.model.SubjectPerCourse;
import com.org.school.services.CoursesService;
import com.org.school.services.PeriodService;
import com.org.school.services.SubjectPerCourseService;
import com.org.school.services.SubjectService;
import com.org.util.enumeration.OperationType;
import com.org.util.safe.ValueHolder;
import com.org.util.web.BaseLazyModel;
import com.org.web.security.views.SecurityBaseView;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class CourseSubjectView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 7657657645443L;

	/** CDI INJECTION POINT */

	@Inject
	private transient SubjectPerCourseService subjectPerCourseService;

	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient SubjectService subjectService;

	@Inject
	private transient PeriodService periodService;

	@Inject
	private transient Identity identity;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Subject, Long> lazySubjectModel;
	private transient BaseLazyModel<SubjectPerCourse, Long> lazySubjecCoursetModel;

	private List<Courses> coursesList;
	private Courses courseSelected;

	private Period periodVigente;

	private List<Subject> subjectsListSelected;

	private List<SubjectPerCourse> subjectCourseListSelected;

	private List<Subject> allSubject;

	@PostConstruct
	public void init() {
		initializateField();
	}

	private void initializateField() {
		coursesList = new ArrayList<Courses>();
		subjectsListSelected = new ArrayList<Subject>();

		subjectCourseListSelected = new ArrayList<SubjectPerCourse>();

		coursesList = coursesService.findAll();
		periodVigente = periodService.findPeriodVigente();
	}

	public void onChangeCourseEvent() {
		if (isNotNullCourseSelected()) {
			loadLazyDataTable();
			clearSelection();
		}
	}

	private void loadLazyDataTable() {
		lazySubjecCoursetModel = new BaseLazyModel<SubjectPerCourse, Long>(subjectPerCourseService);
		lazySubjecCoursetModel.setCustomFilters(courseSubjectLazyFilter());

		lazySubjectModel = new BaseLazyModel<Subject, Long>(subjectService);
		lazySubjectModel.setCustomFilters(loadSubjectLazyFilter());
	}

	private Set<ValueHolder> courseSubjectLazyFilter() {
		Set<ValueHolder> filtros = new HashSet<>();
		ValueHolder customFilter = new ValueHolder("courses.id", courseSelected.getId());
		filtros.add(customFilter);
		return filtros;
	}

	private Set<ValueHolder> loadSubjectLazyFilter() {
		List<SubjectPerCourse> savedSubject = subjectPerCourseService.findSubjectByCourse(courseSelected);

		List<Subject> subjectListSaved = new ArrayList<Subject>();
		for (SubjectPerCourse subjectPerCourse : savedSubject) {
			subjectListSaved.add(subjectPerCourse.getSubject());
		}

		allSubject = new ArrayList<Subject>();
		allSubject = subjectService.findAll();

		Set<ValueHolder> filtros = new HashSet<ValueHolder>();
		List<Subject> diff = substract(allSubject, subjectListSaved);
		List<Long> ids = new ArrayList<>();
		for (Subject subject : diff) {
			ids.add(subject.getId());
		}
		ValueHolder customFilter = new ValueHolder("id", OperationType.IN.getCode(), ids);
		filtros.add(customFilter);
		return filtros;
	}

	public void saveAsociationCourseSubject() {
		if (isNotNullSubjectSelected() && isNotNullPeriod() && isNotNullCourseSelected()) {
			for (Subject subject : subjectsListSelected) {
				SubjectPerCourse subjectPerCourse = new SubjectPerCourse();
				subjectPerCourse.setPeriod(periodVigente);
				subjectPerCourse.setCourses(courseSelected);
				subjectPerCourse.setSubject(subject);
				subjectPerCourseService.save(subjectPerCourse);
			}
			lazySubjectModel = new BaseLazyModel<Subject, Long>(subjectService);
			lazySubjectModel.setCustomFilters(loadSubjectLazyFilter());
			Messages.create("Información").detail("Registros asociados exitosamente").add();
		} else {
			Messages.create("Error").detail("Seleccionar registros").error().add();
		}
		clearSelection();
	}

	public void deleteAsociationCourseSubject() {
		if (isNotNullCourseSubjectSelected()) {
			for (SubjectPerCourse subjectPerCourse : subjectCourseListSelected) {
				subjectPerCourseService.deleteOne(subjectPerCourse);
			}
			Messages.create("Información").detail("Registros eliminados exitosamente").add();
			loadLazyDataTable();
		} else {
			Messages.create("Error").detail("Seleccionar registros").error().add();
		}
	}

	private boolean isNotNullSubjectSelected() {
		return subjectsListSelected != null && !subjectsListSelected.isEmpty();
	}

	private boolean isNotNullCourseSubjectSelected() {
		return subjectCourseListSelected != null && !subjectCourseListSelected.isEmpty();
	}

	private boolean isNotNullPeriod() {
		return periodVigente != null;
	}

	private boolean isNotNullCourseSelected() {
		return courseSelected != null;
	}

	private void clearSelection() {
		subjectsListSelected = new ArrayList<Subject>();
		subjectCourseListSelected = new ArrayList<SubjectPerCourse>();
	}

	private List<Subject> substract(List<Subject> all, List<Subject> selected) {
		int allSize = all.size();
		int selectedSize = selected.size();
		List<Subject> diff = new ArrayList<>(all);
		Long id = 0L;
		for (int i = 0; i < selectedSize; i++) {
			id = selected.get(i).getId();
			for (int j = 0; j < allSize; j++) {
				if (selected.get(i).getId().equals(diff.get(j).getId())) {
					removeFromList(all, id);
				}
			}
		}
		return all;
	}

	private void removeFromList(List<Subject> all, Long id) {
		int sizeGlobal = all.size();
		for (int i = 0; i < sizeGlobal; i++) {
			if (id.equals(all.get(i).getId())) {
				all.remove(i);
				break;
			}
		}
	}

}
