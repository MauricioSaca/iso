package com.org.indicadores.enums;

public enum TrimestralEnum {

	TRIMESTRE_1("T1", "TRIMESTRE 1"),

	TRIMESTRE_2("T2", "TRIMESTRE 2"),

	TRIMESTRE_3("T3", "TRIMESTRE 3"),
	
	TRIMESTRE_4("T4", "TRIMESTRE 4");
	
	String code;

	String description;

	private TrimestralEnum(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TrimestralEnum getTrimestralEnum(final String code) {
		TrimestralEnum ret = null;
		for (TrimestralEnum activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}
	
}
