package com.org.school.enums;

public enum StudentLevel {

	PRIMER_CICLO("PC", "Primer ciclo"),

	SEGUNDO_CICLO("SC", "Segundo ciclo"),

	TERCER_CICLO("TC", "Tercer ciclo");

	String code;

	String description;

	private StudentLevel(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static StudentLevel getStudentLevel(final String code) {
		StudentLevel ret = null;
		for (StudentLevel activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}

}
