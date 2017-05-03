package com.org.school.enums;

public enum StudentStatus {

	APPROVED("A", "Aprobado"),

	REPROBATE("F", "Reprobado");

	String code;

	String description;

	private StudentStatus(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static StudentStatus getStudentStatus(final String code) {
		StudentStatus ret = null;
		for (StudentStatus activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}
	
}
