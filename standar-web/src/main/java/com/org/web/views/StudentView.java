package com.org.web.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.model.Student;
import com.org.school.model.Teacher;
import com.org.school.services.StudentService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class StudentView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private transient StudentService studentService;
	
	private transient Student student;
	private BaseLazyModel<Student, Long> studentLazyData;
	private boolean renderEditView;
	
	@PostConstruct
	public void init(){
		renderEditView = false;
		
		loadTeachers();
	}
	
	public void loadTeachers(){
		studentLazyData = new BaseLazyModel<>(getStudentService());
	}
	
	public void prepareSave(){
		renderEditView = true;
		student = new Student();
	}
	
	public void save(){
		try {
			if(student != null){
				getStudentService().save(student);
				renderEditView = false;
				Messages.create("INFO").detail("Guardado exitosamente").add();			
			}			
		} catch (Exception e) {
			// log
		}
	}
	
	public void prepareUpdate(){
		renderEditView = true;
	}
	
	public void delete(){
		if(student != null){
			getStudentService().delete(student);
			Messages.create("INFO").detail("Eliminado exitosamente").add();			
		}
	}
}
