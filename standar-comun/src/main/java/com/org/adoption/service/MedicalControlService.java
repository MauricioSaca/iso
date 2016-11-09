package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.MedicalControl;
import com.org.adoption.model.Pet;
import com.org.adoption.model.QMedicalControl;
import com.org.adoption.repository.MedicalControlRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class MedicalControlService extends BaseService<MedicalControl, Long> {

	@Inject
	private MedicalControlRepository medicalControlRepository;
	private QMedicalControl qMedicalControl = QMedicalControl.medicalControl;

	@Override
	public BaseRepository<MedicalControl, Long> getRepository() {
		return medicalControlRepository;
	}

	public MedicalControl findByPet(Pet pet) {
		return newJpaQuery().from(qMedicalControl).where(qMedicalControl.pet.id.eq(pet.getId()))
				.singleResult(qMedicalControl);
	}
}
