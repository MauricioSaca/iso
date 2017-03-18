package com.org.web.views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.school.model.Teacher;
import com.org.school.services.TeacherService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class TeacherView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private transient TeacherService teacherService;
	
	private transient Teacher teacher;
	private BaseLazyModel<Teacher, Long> teacherLazyData;
	private boolean renderEditView;
	
	@PostConstruct
	public void init(){
		renderEditView = false;
		
		loadTeachers();
	}
	
	public void loadTeachers(){
		teacherLazyData = new BaseLazyModel<>(getTeacherService());
	}
	
	public void prepareSave(){
		renderEditView = true;
		teacher = new Teacher();
	}
	
	public void save(){
		try {
			if(teacher != null){
				teacherService.save(teacher);
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
		if(teacher != null){
			teacherService.delete(teacher);
			Messages.create("INFO").detail("Eliminado exitosamente").add();			
		}
	}
}
