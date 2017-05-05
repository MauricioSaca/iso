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
import com.org.school.enums.StudentLevel;
import com.org.school.model.Student;
import com.org.school.services.StudentService;
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
public class StudentView implements Serializable {

	private static final long serialVersionUID = 80351893876789L;

	@Inject
	private Identity identity;

	@Inject
	private transient SecurityManagedService securityManagedService;

	@Inject
	private transient StudentService studentService;

	private transient Student student;

	private List<Gender> genderList;
	private List<Shift> shiftList;
	private List<StudentLevel> studentLevelList;

	private BaseLazyModel<Student, Long> studentLazyData;
	private boolean renderEditView;

	// security
	private String userName;
	private User studentUser;
	private String randomPassWord; // random string
	private SecureRandom random;
	private Group studentGroup;
	private Role studentRole;

	@PostConstruct
	public void init() {
		renderEditView = false;

		genderList = Arrays.asList(Gender.values());
		shiftList = Arrays.asList(Shift.values());
		studentLevelList = Arrays.asList(StudentLevel.values());

		loadTeachers();

		// User
		random = new SecureRandom();
		studentGroup = securityManagedService.findGroupByName(GroupsSecurityRolesNames.ESTUDENTS.getCode());
		studentRole = securityManagedService.findRoleByName(RolesSecurityNames.ESTUDENT.getCode());
	}

	public void loadTeachers() {
		studentLazyData = new BaseLazyModel<>(getStudentService());
	}

	public void prepareSave() {
		renderEditView = true;
		student = new Student();

		// security
		userName = StringUtils.EMPTY;
		randomPassWord = StringUtils.EMPTY;
	}

	public void save() {
		try {
			if (student != null) {

				UserTypeEntity studentUserType = createUserForStudent();
				student.setUserTypeEntity(studentUserType);

				getStudentService().save(student);

				String message = "Usted ha sigo registrado en la Fundacion Huellitas. " + "Su nombre de usuario es: "
						+ userName + " clave: " + randomPassWord + " "
						+ "No olvide cambiar su clave al ingresar al sistema.";

				System.out.println(message);

				renderEditView = false;
				Messages.create("INFO").detail("Guardado exitosamente").add();
			}
		} catch (Exception e) {
			// log
		}
	}

	private UserTypeEntity createUserForStudent() {

		studentUser = new User(userName);
		studentUser.setFirstName(student.getName());
		studentUser.setLastName(student.getLastName());
		studentUser.setEmail(student.getEmail());
		studentUser.setAddress(student.getAddress());
		studentUser.setTelephone(student.getCellPhone());

		randomPassWord = generateRandomPass();
		Password randomPass = new Password(randomPassWord);
		UserTypeEntity user = new UserTypeEntity();
		boolean areNotNullRoleAndGroup = studentGroup != null && studentRole != null;
		if (areNotNullRoleAndGroup) {
			securityManagedService.saveUser(studentUser, randomPass, studentGroup, studentRole);
			user.setId(studentUser.getId());
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
		if (student != null) {
			getStudentService().delete(student);
			Messages.create("INFO").detail("Eliminado exitosamente").add();
		}
	}
}
