package com.org.adoption.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.expr.BooleanExpression;
import com.org.adoption.model.AdoptedPets;
import com.org.adoption.model.Pet;
import com.org.adoption.model.Postulant;
import com.org.adoption.model.QAdoptedPets;
import com.org.adoption.repository.AdoptedPetsRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class AdoptedPetsService extends BaseService<AdoptedPets, Long>{

	
	@Inject
	private AdoptedPetsRepository adoptedPetsRepository;
	private QAdoptedPets qAdoptedPets = QAdoptedPets.adoptedPets;
	
	@Override
	public BaseRepository<AdoptedPets, Long> getRepository() {
		return adoptedPetsRepository;
	}
	
	public List<AdoptedPets> findByAdoptante(Postulant postulante){
		BooleanExpression byPost = qAdoptedPets.postulant.id.eq(postulante.getId());
		
		return newJpaQuery().from(qAdoptedPets).where(byPost).list(qAdoptedPets);
	}
	
	public AdoptedPets findByPostAndPet(Postulant postulante, Pet pet){
		BooleanExpression byPost = qAdoptedPets.postulant.id.eq(postulante.getId());
		BooleanExpression byPet = qAdoptedPets.pet.id.eq(pet.getId());
		
		return newJpaQuery().from(qAdoptedPets).where(byPost.and(byPet)).singleResult(qAdoptedPets);
	}

}
