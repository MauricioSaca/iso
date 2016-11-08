package com.org.adoption.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.Projections;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.model.PostulantQuestions;
import com.org.adoption.model.QPostulantAnswerEvaluation;
import com.org.adoption.model.QPostulantQuestions;
import com.org.adoption.repository.PostulantQuestionsRepository;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PostulantQuestionsService extends BaseService<PostulantQuestions, Long> {

	@Inject
	private PostulantQuestionsRepository postulantQuestionsRepository;
	private QPostulantQuestions qPostulantQuestions = QPostulantQuestions.postulantQuestions;
	private QPostulantAnswerEvaluation qPostulantAnswerEvaluation = QPostulantAnswerEvaluation.postulantAnswerEvaluation;

	@Override
	public BaseRepository<PostulantQuestions, Long> getRepository() {
		return postulantQuestionsRepository;
	}

	public List<PostulantAnswerEvaluation> getQuestions() {
		BooleanExpression id = qPostulantQuestions.id.eq(qPostulantQuestions.id);
		return newJpaQuery().from(qPostulantQuestions).list(Projections
				.constructor(PostulantAnswerEvaluation.class, qPostulantQuestions));
	}
}
