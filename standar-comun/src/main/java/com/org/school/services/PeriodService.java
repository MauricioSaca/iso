package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Period;
import com.org.school.model.QPeriod;
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

	public Period findPeriodVigente() {
		BooleanExpression byVigencia = QPeriod.period.vigente.eq(true);
		return newJpaQuery().from(QPeriod.period).where(byVigencia).singleResult(QPeriod.period);
	}

}
