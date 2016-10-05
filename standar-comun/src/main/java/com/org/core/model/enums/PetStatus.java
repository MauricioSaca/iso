package com.org.core.model.enums;

public enum PetStatus {

	ADOPTED("ADTE", "ADOPTADO"),
	 
	ADOPTION("ADTION", "EN ADOPCION"),
	
	JOINED("JOIN","INGRESADO"),
	
	RECUPERATION("RECOR","RECUPERACION");
	
	String code;

	String description;

	private PetStatus(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PetStatus getPetStatus(final String code) {
		PetStatus ret = null;
        for (PetStatus activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
	
}
