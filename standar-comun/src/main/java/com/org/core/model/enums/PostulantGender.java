package com.org.core.model.enums;

public enum PostulantGender {

	FEMALE("F", "FEMENINO"),
	 
	MALE("H", "MASCULINO");
	
	String code;

	String description;

	private PostulantGender(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PostulantGender getPostulantGender(final String code) {
		PostulantGender ret = null;
        for (PostulantGender activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}	
}
