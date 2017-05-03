package com.org.school.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.school.model.Department;
import com.org.school.repository.DepartmentRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class DepartmentService extends BaseService<Department, Long> {

	@Inject
	private DepartmentRepository departmentRepository;

	@Override
	public BaseRepository<Department, Long> getRepository() {
		return departmentRepository;
	}

}
