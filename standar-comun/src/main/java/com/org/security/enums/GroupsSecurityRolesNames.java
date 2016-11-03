package com.org.security.enums;

public enum GroupsSecurityRolesNames {

	ADMINS("admins","Administradores"),
	
	POSTULANDS("postulands","Postulantes"),
	
	ORGANIZERS("organizers","Organizacionales"),
	
	REPORTS("reports","Reporteria"),
	
	MANAGERS("managers","Gerencial");
	
	String code;

	String description;

	private GroupsSecurityRolesNames(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static GroupsSecurityRolesNames getGroupsSecurityNames(final String code) {
		GroupsSecurityRolesNames ret = null;
        for (GroupsSecurityRolesNames activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
	
}
