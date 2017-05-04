package com.org.school.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.school.model.Department;
import com.org.school.model.Municipal;
import com.org.school.model.QDepartment;
import com.org.school.model.QMunicipal;
import com.org.school.repository.MunicipalRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class MunicipalService extends BaseService<Municipal, Long> {

	@Inject
	private MunicipalRepository municipalRepository;

	@Override
	public BaseRepository<Municipal, Long> getRepository() {
		return municipalRepository;
	}

	public List<Municipal> findMunicipalByDepartament(Department departament) {
		BooleanExpression byDepartament = QMunicipal.municipal.department.id.eq(departament.getId());
		return (List<Municipal>) findAll(byDepartament);
	}

}
