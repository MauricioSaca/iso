package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.MedicalControl;
import com.org.adoption.repository.MedicalControlRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class MedicalControlService extends BaseService<MedicalControl, Long>{

	@Inject
	private MedicalControlRepository medicalControlRepository;
	
	@Override
	public BaseRepository<MedicalControl, Long> getRepository() {
		return medicalControlRepository;
	}

}
