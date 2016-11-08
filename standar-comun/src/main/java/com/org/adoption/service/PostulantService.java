package com.org.adoption.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Postulant;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.model.QPostulantAnswerEvaluation;
import com.org.adoption.repository.PostulantRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PostulantService extends BaseService<Postulant, Long> {

	@Inject
	private PostulantRepository postulantRepository;
	private QPostulantAnswerEvaluation qPostulantAnswerEvaluation = QPostulantAnswerEvaluation.postulantAnswerEvaluation;
	
	@Override
	public BaseRepository<Postulant, Long> getRepository() {
		return postulantRepository;
	}

	public List<PostulantAnswerEvaluation> findQuestions(Postulant postulant) {

		return newJpaQuery().from(qPostulantAnswerEvaluation)
				.where(qPostulantAnswerEvaluation.postulant.id.eq(postulant.getId()))
				.list(qPostulantAnswerEvaluation);
	}
}
