package com.org.core.model.enums;

public enum PostulantStatus {

	APPROVED("APRO", "APROBADO"),
	 
	REJECTED("REJECT", "RECHAZADO"),
	
	IN_PROCESS("EP","EN PROCESO"),
	
	VERIFIED("VERIF","VERIFICADO");
	
	String code;

	String description;

	private PostulantStatus(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PostulantStatus getPetStatus(final String code) {
		PostulantStatus ret = null;
        for (PostulantStatus activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
	
}
