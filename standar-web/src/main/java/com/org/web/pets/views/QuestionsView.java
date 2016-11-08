package com.org.web.pets.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.org.adoption.model.PostulantQuestions;
import com.org.adoption.service.PostulantQuestionsService;
import com.org.core.model.enums.TypeQuestion;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class QuestionsView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456787809L;

	@Inject
	private transient PostulantQuestionsService postulantQuestionsService;

	private transient BaseLazyModel<PostulantQuestions, Long> questionsLazyData;
	private PostulantQuestions selectedQuestion;
	private List<TypeQuestion> typeQuestionList;
	/**
	 * TRUE for create FALSE for edit
	 */
	private boolean createOrEdit;

	@PostConstruct
	public void init() {
		selectedQuestion = new PostulantQuestions();
		this.loadData();
		typeQuestionList = Arrays.asList(TypeQuestion.values());
		createOrEdit = true;
	}

	public void loadData() {
		questionsLazyData = new BaseLazyModel<PostulantQuestions, Long>(getPostulantQuestionsService());
	}
	
	public void prepareCreate() {
		createOrEdit = true;
		selectedQuestion = new PostulantQuestions();
	}

	public void add() {
		try {

			postulantQuestionsService.save(selectedQuestion);

			Messages.create("REGISTRO").detail("Registro agregado exitosamente").add();
			selectedQuestion = new PostulantQuestions();
			
		} catch (Exception e) {
			selectedQuestion = new PostulantQuestions();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
	}

	public void prepareUpdate() {
		createOrEdit = false;
	}

	public void update() {
		try {
			postulantQuestionsService.save(selectedQuestion);
			Messages.create("REGISTRO").detail("Registro actualizado exitosamente").add();
			selectedQuestion = new PostulantQuestions();
			createOrEdit = true;
		} catch (Exception e) {
			selectedQuestion = new PostulantQuestions();
			createOrEdit = true;
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
	}

	public void delete(PostulantQuestions question) {
		postulantQuestionsService.deleteOne(question);
		Messages.create("REGISTRO").detail("Eliminado exitosamente").add();
	}
}
