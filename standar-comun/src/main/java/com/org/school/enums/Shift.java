package com.org.school.enums;

public enum Shift {

	MORNING("MAT", "Matutino"),

	EVENING("VES", "Vespertino");

	String code;

	String description;

	private Shift(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Shift getShift(final String code) {
		Shift ret = null;
		for (Shift activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}

}
