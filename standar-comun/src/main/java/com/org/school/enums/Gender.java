package com.org.school.enums;

public enum Gender {

	MALE("M", "Masculino"),

	FEMALE("F", "Femenino");

	String code;

	String description;

	private Gender(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Gender getGender(final String code) {
		Gender ret = null;
		for (Gender activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}
	
}
