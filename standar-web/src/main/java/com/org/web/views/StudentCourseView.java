package com.org.web.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.school.enums.StudentLevel;
import com.org.school.model.Courses;
import com.org.school.model.Period;
import com.org.school.model.Student;
import com.org.school.model.StudentsPerCourse;
import com.org.school.model.Subject;
import com.org.school.model.SubjectPerCourse;
import com.org.school.services.CoursesService;
import com.org.school.services.PeriodService;
import com.org.school.services.StudentService;
import com.org.school.services.StudentsPerCourseService;
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
public class StudentCourseView extends SecurityBaseView implements Serializable {

	private static final long serialVersionUID = 4329870920L;

	/** CDI INJECTION POINT */

	@Inject
	private transient StudentsPerCourseService studentsPerCourseService;

	@Inject
	private transient StudentService studentService;

	@Inject
	private transient CoursesService coursesService;

	@Inject
	private transient PeriodService periodService;

	@Inject
	private transient Identity identity;

	/** INSTANCE FIELD */

	private transient BaseLazyModel<Student, Long> lazysStudentModel;
	private transient BaseLazyModel<StudentsPerCourse, Long> lazyStudentCourseModel;

	private List<Courses> coursesList;
	private Courses courseSelected;

	private Period periodVigente;

	private List<Student> studentListSelected;

	private List<StudentLevel> studentLevelList;
	private StudentLevel studentLevelSelected;

	private List<StudentsPerCourse> studentCourseListSelected;

	private List<Student> allStudent;

	@PostConstruct
	public void init() {
		initializateField();
	}

	private void initializateField() {
		
		studentListSelected = new ArrayList<Student>();

		studentCourseListSelected = new ArrayList<StudentsPerCourse>();

		
		periodVigente = periodService.findPeriodVigente();

		studentLevelList = Arrays.asList(StudentLevel.values());
	}

	public void onChangeCourseEvent() {
		if (isNotNullCourseSelected() && isNotNullLevelSelected()) {
			loadLazyDataTable();
			clearSelection();
		}
	}

	public void onChangeLevelEvent() {
		if (isNotNullLevelSelected()) {
			coursesList = new ArrayList<Courses>();
			coursesList = coursesService.findCoursesByLevel(studentLevelSelected);
			clearSelection();
		}
	}

	private void loadLazyDataTable() {
		lazysStudentModel = new BaseLazyModel<Student, Long>(studentService);
		lazysStudentModel.setCustomFilters(loadStudentLazyFilter());

		lazyStudentCourseModel = new BaseLazyModel<StudentsPerCourse, Long>(studentsPerCourseService);
		lazyStudentCourseModel.setCustomFilters(studentCourseLazyFilter());
	}

	private Set<ValueHolder> studentCourseLazyFilter() {
		Set<ValueHolder> filtros = new HashSet<>();
		ValueHolder customFilter = new ValueHolder("courses.id", courseSelected.getId());
		filtros.add(customFilter);

		ValueHolder customFilterStudent = new ValueHolder("student.studentLevel", studentLevelSelected.getCode());
		filtros.add(customFilterStudent);

		return filtros;
	}

	private Set<ValueHolder> loadStudentLazyFilter() {
		List<StudentsPerCourse> savedStudent = studentsPerCourseService.findStudentsListByCourse(courseSelected);

		List<Student> studentListSaved = new ArrayList<Student>();
		for (StudentsPerCourse studentsPerCourse : savedStudent) {
			studentListSaved.add(studentsPerCourse.getStudent());
		}

		allStudent = new ArrayList<Student>();
		allStudent = studentService.findAll();

		Set<ValueHolder> filtros = new HashSet<ValueHolder>();
		List<Student> diff = substract(allStudent, studentListSaved);
		List<Long> ids = new ArrayList<>();
		for (Student student : diff) {
			ids.add(student.getId());
		}
		ValueHolder customFilter = new ValueHolder("id", OperationType.IN.getCode(), ids);
		ValueHolder customFilterLevel = new ValueHolder("studentLevel", studentLevelSelected.getCode());

		filtros.add(customFilter);
		filtros.add(customFilterLevel);

		return filtros;
	}
	
	public void saveAsociationCourseStudent() {
		if (isNotNullStudentSelected() && isNotNullPeriod() && isNotNullCourseSelected()) {
			for (Student student : studentListSelected) {
				StudentsPerCourse studentsPerCourse = new StudentsPerCourse();
				studentsPerCourse.setPeriod(periodVigente);
				studentsPerCourse.setCourses(courseSelected);
				studentsPerCourse.setStudent(student);
				studentsPerCourseService.save(studentsPerCourse);
			}
			
			lazysStudentModel = new BaseLazyModel<Student, Long>(studentService);
			lazysStudentModel.setCustomFilters(loadStudentLazyFilter());
			
			Messages.create("Información").detail("Registros asociados exitosamente").add();
		} else {
			Messages.create("Error").detail("Seleccionar registros").error().add();
		}
		clearSelection();
	}

	public void deleteAsociationCourseStudent() {
		if (isNotNullStudentCourseSelected()) {
			for (StudentsPerCourse studentsPerCourse : studentCourseListSelected) {
				studentsPerCourseService.deleteOne(studentsPerCourse);
			}
			Messages.create("Información").detail("Registros eliminados exitosamente").add();
			loadLazyDataTable();
		} else {
			Messages.create("Error").detail("Seleccionar registros").error().add();
		}
	}

	private boolean isNotNullPeriod() {
		return periodVigente != null;
	}

	private boolean isNotNullCourseSelected() {
		return courseSelected != null;
	}

	private boolean isNotNullLevelSelected() {
		return studentLevelSelected != null;
	}

	private boolean isNotNullStudentSelected() {
		return studentListSelected != null && !studentListSelected.isEmpty();
	}

	private boolean isNotNullStudentCourseSelected() {
		return studentCourseListSelected != null && !studentCourseListSelected.isEmpty();
	}

	private void clearSelection() {
		studentListSelected = new ArrayList<Student>();
		studentCourseListSelected = new ArrayList<StudentsPerCourse>();
	}

	private List<Student> substract(List<Student> all, List<Student> selected) {
		int allSize = all.size();
		int selectedSize = selected.size();
		List<Student> diff = new ArrayList<>(all);
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

	private void removeFromList(List<Student> all, Long id) {
		int sizeGlobal = all.size();
		for (int i = 0; i < sizeGlobal; i++) {
			if (id.equals(all.get(i).getId())) {
				all.remove(i);
				break;
			}
		}
	}

}
