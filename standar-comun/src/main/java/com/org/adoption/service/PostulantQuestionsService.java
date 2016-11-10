package com.org.adoption.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mysema.query.types.Projections;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.adoption.model.Postulant;
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
		return newJpaQuery().from(qPostulantQuestions)
				.list(Projections.constructor(PostulantAnswerEvaluation.class, qPostulantQuestions));
	}

	public List<PostulantAnswerEvaluation> findQuestionsAnswerByPostulant(Postulant postulant) {
		BooleanExpression idPost = qPostulantAnswerEvaluation.postulant.id.eq(postulant.getId());
		return newJpaQuery().from(qPostulantAnswerEvaluation).where(idPost)
				.list(qPostulantAnswerEvaluation);
	}
}
