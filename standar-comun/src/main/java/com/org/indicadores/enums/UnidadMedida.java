package com.org.indicadores.enums;

public enum UnidadMedida {

	CURRENCY("$", "MONETARIO"),

	PORCENTAJE("%", "PORCENTAJE"),

	NUMERIC("#", "NUMERICO");

	String code;

	String description;

	private UnidadMedida(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static UnidadMedida getUnidadMedida(final String code) {
		UnidadMedida ret = null;
		for (UnidadMedida activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}

}
