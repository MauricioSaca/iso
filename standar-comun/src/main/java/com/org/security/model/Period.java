package com.org.security.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
@Setter
public class Period implements Serializable {

	private static final long serialVersionUID = -901995125212178550L;

	@Column(nullable = true)
	@Transient
	private Boolean vigente;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public Boolean getVigente() {
		boolean vigente = false;
		Calendar today = Calendar.getInstance();
		Calendar start = null;
		Calendar end = null;

		if (getStartDate() != null) {
			start = Calendar.getInstance();
			start.setTime(getStartDate());
		}

		if (getEndDate() != null) {
			end = Calendar.getInstance();
			end.setTime(getEndDate());
		}

		if (start != null && end != null) {
			if ((start.before(today) || start.equals(today)) && end.after(today)) {
				vigente = true;
			} else if (end.get(Calendar.DATE) == (today.get(Calendar.DATE))
					&& end.get(Calendar.DAY_OF_MONTH) == (today.get(Calendar.DAY_OF_MONTH))
					&& end.get(Calendar.DAY_OF_YEAR) == (today.get(Calendar.DAY_OF_YEAR))) {
				vigente = true;
			}
		} else if (start != null && (start.before(today) || start.equals(today))) {
			vigente = true;
		}

		return vigente;
	}

}
