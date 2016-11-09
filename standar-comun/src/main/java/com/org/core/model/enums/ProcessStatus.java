package com.org.core.model.enums;

public enum ProcessStatus {

	SOLICITADO("SOL", "SOLICITADO"),
	 
	ENPROCESO("ENP", "EN PROCESO"),
	
	APROBADO("APR","APROBADO"),
	
	DENEGADO("DEN","DENEGADO");
	
	String code;

	String description;

	private ProcessStatus(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static ProcessStatus getProcessStatus(final String code) {
		ProcessStatus ret = null;
        for (ProcessStatus activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
	
}
