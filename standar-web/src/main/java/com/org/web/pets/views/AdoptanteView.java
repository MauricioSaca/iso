package com.org.web.pets.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import com.google.common.base.Strings;
import com.org.adoption.model.Postulant;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.service.PostulantAnswerEvaluationService;
import com.org.adoption.service.PostulantQuestionsService;
import com.org.adoption.service.PostulantService;
import com.org.core.model.enums.PostulantGender;
import com.org.core.model.enums.PostulantStatus;
import com.org.security.service.SecurityManagedService;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class AdoptanteView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456787809L;

	@Inject
	private transient PostulantService postulantService;

	@Inject
	private transient PostulantQuestionsService postulantQuestionsService;
	
	@Inject
	private transient PostulantAnswerEvaluationService postulantAnswerEvaluationService;
	
	@Inject
	private transient SecurityManagedService securityManagedService;

	private transient BaseLazyModel<Postulant, Long> adoptanteLazyData;
	private Postulant selectedAdoptante;
	private List<PostulantStatus> adoptanteStatusList;
	private List<PostulantGender> postulantGenderList;
	private List<PostulantAnswerEvaluation> questions;
	private byte[] file;
	/**
	 * TRUE for create FALSE for edit
	 */
	private boolean createOrEdit;

	/**
	 * TRUE for add/edit, FALSE for list view
	 */
	private boolean renderEditView = false;

	@PostConstruct
	public void init() {
		selectedAdoptante = new Postulant();
		this.loadData();
		adoptanteStatusList = Arrays.asList(PostulantStatus.values());
		postulantGenderList = Arrays.asList(PostulantGender.values());

		// postulantService.findQuestions();//agarra usuario de la seguridad
		questions = postulantQuestionsService.getQuestions();
	}

	public void loadData() {
		adoptanteLazyData = new BaseLazyModel<Postulant, Long>(getPostulantService());
	}

	public void showEditPanel() {
		selectedAdoptante = new Postulant();
		renderEditView = true;
		createOrEdit = true;
	}

	public void getBack() {
		renderEditView = false;
		createOrEdit = true;
	}

	public void replaceChar() {
		if (!Strings.isNullOrEmpty(selectedAdoptante.getPhone())) {
			selectedAdoptante.setPhone(selectedAdoptante.getPhone().replaceAll("-", ""));
		}

		selectedAdoptante.setCellPhone(selectedAdoptante.getCellPhone().replaceAll("-", ""));
		selectedAdoptante.setDui(selectedAdoptante.getDui().replaceAll("-", ""));
	}

	public void add() {
		try {

			replaceChar();
			//selectedAdoptante.setUserTypeEntity(userTypeEntity); 
			//FIXME guardar usuario y setearlo
			postulantService.save(selectedAdoptante);

			Messages.create("REGISTRO").detail("Registro agregado exitosamente").add();
			renderEditView = false;
			selectedAdoptante = new Postulant();

		} catch (Exception e) {
			selectedAdoptante = new Postulant();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
	}

	public void prepareUpdate() {
		createOrEdit = false;
		renderEditView = true;
	}

	public void update() {
		try {
			replaceChar();
			postulantService.save(selectedAdoptante);
			Messages.create("REGISTRO").detail("Registro actualizado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedAdoptante = new Postulant();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
		selectedAdoptante = new Postulant();
	}

	public void delete(Postulant postulant) {
		postulantService.deleteOne(postulant);
		Messages.create("REGISTRO").detail("Eliminado exitosamente").add();
	}

	public void saveQuiz() {
		PostulantAnswerEvaluation postulantAnswerEvaluation = new PostulantAnswerEvaluation();
		List<PostulantAnswerEvaluation> postulantAnswerEvaluationList = new ArrayList<PostulantAnswerEvaluation>();

		for (PostulantAnswerEvaluation evaluation : questions) {
			postulantAnswerEvaluation = new PostulantAnswerEvaluation();
			postulantAnswerEvaluation.setPostulant(new Postulant(1L));
			postulantAnswerEvaluation.setPostulantQuestions(evaluation.getPostulantQuestions());
			postulantAnswerEvaluation.setAnswer(evaluation.getAnswer());
			postulantAnswerEvaluationList.add(postulantAnswerEvaluation);
		}
		
		postulantAnswerEvaluationService.save(postulantAnswerEvaluationList);
		Messages.create("PREGUNTAS").detail("Guardado satisfactoriamente").add();
	}
}
