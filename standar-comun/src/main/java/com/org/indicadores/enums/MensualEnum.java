package com.org.indicadores.enums;

public enum MensualEnum {

	ENERO("1", "Enero"),

	FEBRERO("2", "Febrero"),

	MARZO("3", "Marzo"),

	ABRIL("4", "Abril"),

	MAYO("5", "Mayo"),

	JUNIO("6", "Junio"),

	JULIO("7", "Julio"),

	AGOSTO("8", "Agosto"),

	SEPTIEMBRE("9", "Septiembre"),

	OCTUBRE("10", "Octubre"),

	NOVIEMBRE("11", "Noviembre"),

	DICIEMBRE("12", "Diciembre");

	String code;

	String description;

	private MensualEnum(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static MensualEnum getMensualEnum(final String code) {
		MensualEnum ret = null;
		for (MensualEnum activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}

}
