package com.org.indicadores.enums;

public enum EvaluationLevel {

	
	EXCELENTE("E","EXCELENTE"),
	
	MUY_BUENO("MB","MUY BUENO"),
	
	BUENO("B","BUENO"),
	
	MALO("M","MALO");
	
	String code;

	String description;

	private EvaluationLevel(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static EvaluationLevel getEvaluationLevel(final String code) {
		EvaluationLevel ret = null;
        for (EvaluationLevel activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
	
}
