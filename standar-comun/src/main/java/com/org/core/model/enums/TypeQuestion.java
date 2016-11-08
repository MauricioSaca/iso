package com.org.core.model.enums;

public enum TypeQuestion {

	OPEN("OP", "ABIERTA"),
	 
	YES_NO("YN", "SI O NO"),
	
	MULTIPLE_OPTION("MULTI","SELECCION MULTIPLE");
	
	String code;

	String description;

	private TypeQuestion(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TypeQuestion getTypeQuestion(final String code) {
		TypeQuestion ret = null;
        for (TypeQuestion activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
}
