package com.org.indicadores.enums;

public enum EvaluationPeriod {

	
	ANUAL("AL","ANUAL"),
	
	TRIMESTRAL("TRIME","TRIMESTRAL"),
	
	MENSUAL("ME","MENSUAL");
	
	String code;

	String description;

	private EvaluationPeriod(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static EvaluationPeriod getEvaluationPeriod(final String code) {
		EvaluationPeriod ret = null;
        for (EvaluationPeriod activeEnum : values()) {
            if (activeEnum.getCode().equals(code)) {
                ret = activeEnum;
                break;
            }
        }
		return ret;
	}
	
}
