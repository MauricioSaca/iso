package com.org.adoption.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.repository.PostulantAnswerEvaluationRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PostulantAnswerEvaluationService extends BaseService<PostulantAnswerEvaluation, Long>{

	@Inject
	private PostulantAnswerEvaluationRepository postulantAnswerEvaluationRepository;
	
	@Override
	public BaseRepository<PostulantAnswerEvaluation, Long> getRepository() {
		return postulantAnswerEvaluationRepository;
	}

}
