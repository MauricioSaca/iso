package com.org.web.views;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;
import org.picketlink.idm.credential.Password;

import com.org.school.enums.Gender;
import com.org.school.enums.Shift;
import com.org.school.model.Teacher;
import com.org.school.services.TeacherService;
import com.org.security.enums.GroupsSecurityRolesNames;
import com.org.security.enums.RolesSecurityNames;
import com.org.security.identity.model.UserTypeEntity;
import com.org.security.identity.stereotype.Group;
import com.org.security.identity.stereotype.Role;
import com.org.security.identity.stereotype.User;
import com.org.security.service.SecurityManagedService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class TeacherView implements Serializable {

	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private Identity identity;

	@Inject
	private transient SecurityManagedService securityManagedService;

	@Inject
	private transient TeacherService teacherService;

	private transient Teacher teacher;

	private List<Gender> genderList;
	private List<Shift> shiftList;

	private BaseLazyModel<Teacher, Long> teacherLazyData;
	private boolean renderEditView;

	// security
	private String userName;
	private User teacherUser;
	private String randomPassWord; // random string
	private SecureRandom random;
	private Group teacherGroup;
	private Role teacherRole;

	@PostConstruct
	public void init() {
		renderEditView = false;

		genderList =  Arrays.asList(Gender.values());
		shiftList = Arrays.asList(Shift.values());
		
		loadTeachers();

		// User
		random = new SecureRandom();
		teacherGroup = securityManagedService.findGroupByName(GroupsSecurityRolesNames.TEACHERS.getCode());
		teacherRole = securityManagedService.findRoleByName(RolesSecurityNames.TEACHER.getCode());
	}

	public void loadTeachers() {
		teacherLazyData = new BaseLazyModel<>(getTeacherService());
	}

	public void prepareSave() {
		renderEditView = true;
		teacher = new Teacher();

		// security
		userName = StringUtils.EMPTY;
		randomPassWord = StringUtils.EMPTY;
	}

	public void save() {
		try {
			if (teacher != null) {
				UserTypeEntity teacherUserType = createUserForTeacher();
				teacher.setUserTypeEntity(teacherUserType);
				teacherService.save(teacher);
				
				
				String message = "Usted ha sigo registrado."
						+ "Su nombre de usuario es: " + userName + " clave: " + randomPassWord + " "
								+ "No olvide cambiar su clave al ingresar al sistema.";
				
				System.out.println(message);
				
				renderEditView = false;
				Messages.create("INFO").detail("Guardado exitosamente").add();
			}
		} catch (Exception e) {
			// log
		}
	}

	private UserTypeEntity createUserForTeacher() {

		teacherUser = new User(userName);
		teacherUser.setFirstName(teacher.getName());
		teacherUser.setLastName(teacher.getLastName());
		teacherUser.setEmail(teacher.getEmail());
		teacherUser.setAddress(teacher.getAddress());
		teacherUser.setTelephone(teacher.getCellPhone());

		randomPassWord = generateRandomPass();
		Password randomPass = new Password(randomPassWord);
		UserTypeEntity user = new UserTypeEntity();
		boolean areNotNullRoleAndGroup = teacherGroup != null && teacherRole != null;
		if (areNotNullRoleAndGroup) {
			securityManagedService.saveUser(teacherUser, randomPass, teacherGroup, teacherRole);
			user.setId(teacherUser.getId());
		}

		return user;
	}

	private String generateRandomPass() {
		return new BigInteger(130, random).toString(32);
	}

	public void prepareUpdate() {
		renderEditView = true;
	}

	public void delete() {
		if (teacher != null) {
			teacherService.delete(teacher);
			Messages.create("INFO").detail("Eliminado exitosamente").add();
		}
	}
}
