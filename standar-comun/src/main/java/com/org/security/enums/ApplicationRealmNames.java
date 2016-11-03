package com.org.security.enums;

public enum ApplicationRealmNames {

	ADOPTION("Adoption", "Aplicacion de adopcion de mascotas");

	String code;

	String description;

	private ApplicationRealmNames(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static ApplicationRealmNames getApplicationRealmNames(final String code) {
		ApplicationRealmNames ret = null;
		for (ApplicationRealmNames activeEnum : values()) {
			if (activeEnum.getCode().equals(code)) {
				ret = activeEnum;
				break;
			}
		}
		return ret;
	}

}
