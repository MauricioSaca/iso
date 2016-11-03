package com.org.core.model.enums;

public enum PetType {

	CAT("cat", "Gato"),
	 
	DOG("dog", "Perro");
	
	String code;

	String description;

	private PetType(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PetType getPetType(final String code) {
		PetType ret = null;
        for (PetType activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}	
}
