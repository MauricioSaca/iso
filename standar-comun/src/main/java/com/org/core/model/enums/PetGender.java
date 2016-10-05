package com.org.core.model.enums;

public enum PetGender {

	FEMALE("F", "FEMENINO"),
	 
	MALE("M", "MASCULINO");
	
	String code;

	String description;

	private PetGender(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PetGender getPetGender(final String code) {
		PetGender ret = null;
        for (PetGender activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}	
}
