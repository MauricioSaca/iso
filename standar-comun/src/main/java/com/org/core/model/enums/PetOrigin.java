package com.org.core.model.enums;

public enum PetOrigin {

	ABANDONED("ABA", "ABANDONADO"),
	 
	FAMILY_ADOPTION("FA", "FAMILIA DA EN ADOPCION");
	
	String code;

	String description;

	private PetOrigin(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PetOrigin getPetOrigin(final String code) {
		PetOrigin ret = null;
        for (PetOrigin activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
}
