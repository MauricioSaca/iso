package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Period;
import com.org.school.repository.PeriodRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PeriodService extends BaseService<Period, Long> {

	@Inject
	private PeriodRepository periodRepository;

	@Override
	public BaseRepository<Period, Long> getRepository() {
		return periodRepository;
	}

}
