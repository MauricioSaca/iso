package com.org.adoption.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.org.adoption.model.Postulant;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.model.QPostulant;
import com.org.adoption.model.QPostulantAnswerEvaluation;
import com.org.adoption.repository.PostulantRepository;
import com.org.security.identity.stereotype.User;
import com.org.util.repository.BaseRepository;
import com.org.util.service.BaseService;

@Stateless
public class PostulantService extends BaseService<Postulant, Long> {

	@Inject
	private PostulantRepository postulantRepository;
	private QPostulantAnswerEvaluation qPostulantAnswerEvaluation = QPostulantAnswerEvaluation.postulantAnswerEvaluation;
	private QPostulant qPostulant = QPostulant.postulant;

	@Override
	public BaseRepository<Postulant, Long> getRepository() {
		return postulantRepository;
	}

	public List<PostulantAnswerEvaluation> findQuestions(Postulant postulant) {

		return newJpaQuery().from(qPostulantAnswerEvaluation)
				.where(qPostulantAnswerEvaluation.postulant.id.eq(postulant.getId())).list(qPostulantAnswerEvaluation);
	}

	public Postulant findByUser(User user) {

		return newJpaQuery().from(qPostulant).where(qPostulant.userTypeEntity.id.eq(user.getId()))
				.singleResult(qPostulant);
	}

	public boolean existDui(String dui) {
		List<Postulant> list = newJpaQuery().from(qPostulant).where(qPostulant.dui.eq(dui)).list(qPostulant);

		return list != null && !list.isEmpty();
	}

	public boolean existMail(String email) {
		List<Postulant> list = newJpaQuery().from(qPostulant).where(qPostulant.email.eq(email)).list(qPostulant);

		return list != null && !list.isEmpty();
	}
}
