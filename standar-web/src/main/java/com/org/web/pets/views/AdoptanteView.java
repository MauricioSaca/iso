package com.org.web.pets.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;

import com.google.common.base.Strings;
import com.org.adoption.model.Pet;
import com.org.adoption.model.Postulant;
import com.org.adoption.service.PetService;
import com.org.adoption.service.PostulantService;
import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;
import com.org.core.model.enums.PetType;
import com.org.core.model.enums.PostulantGender;
import com.org.core.model.enums.PostulantStatus;
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

	private transient BaseLazyModel<Postulant, Long> adoptanteLazyData;
	private Postulant selectedAdoptante;
	private List<PostulantStatus> adoptanteStatusList;
	private List<PostulantGender> postulantGenderList;

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

	public void replaceChar(){
		if(!Strings.isNullOrEmpty(selectedAdoptante.getPhone())){
			selectedAdoptante.setPhone(selectedAdoptante.getPhone().replaceAll("-", ""));
		}
		
		selectedAdoptante.setCellPhone(selectedAdoptante.getCellPhone().replaceAll("-", ""));
		selectedAdoptante.setDui(selectedAdoptante.getDui().replaceAll("-", ""));
	}
	
	public void add() {
		try {
			
			replaceChar();
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
}
