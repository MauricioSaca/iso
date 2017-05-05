package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Period;
import com.org.school.model.QTrimesters;
import com.org.school.model.Trimesters;
import com.org.school.repository.TrimestersRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class TrimestersService extends BaseService<Trimesters, Long> {

	@Inject
	private TrimestersRepository trimestersRepository;

	@Override
	public BaseRepository<Trimesters, Long> getRepository() {
		return trimestersRepository;
	}

	public List<Trimesters> findTrimesterByPeriod(Period period) {
		BooleanExpression byPeriod = QTrimesters.trimesters.period.id.eq(period.getId());
		return (List<Trimesters>) findAll(byPeriod);
	}

}
