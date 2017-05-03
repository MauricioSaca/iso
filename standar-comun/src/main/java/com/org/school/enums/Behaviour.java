package com.org.school.enums;

public enum Behaviour {

	BAD("NM","Necesita mejorar"),
	
	GOD("E","Excelente");
	
	String code;

	String description;

	private Behaviour(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Behaviour getBehaviour(final String code) {
		Behaviour ret = null;
		for (Behaviour activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}
	
}
