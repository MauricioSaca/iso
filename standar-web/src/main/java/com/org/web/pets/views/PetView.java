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

import com.org.adoption.model.Pet;
import com.org.adoption.service.PetService;
import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;
import com.org.core.model.enums.PetType;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PetView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456787809L;

	@Inject
	private transient PetService petService;

	private transient BaseLazyModel<Pet, Long> petLazyData;
	private Pet selectedPet;
	private List<PetOrigin> petOriginList;
	private List<PetStatus> petStatusList;
	private List<PetGender> petGenderList;
	private List<PetType> petTypeList;
	
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
		selectedPet = new Pet();
		this.loadData();
		petOriginList = Arrays.asList(PetOrigin.values());
		petStatusList = Arrays.asList(PetStatus.values());
		petGenderList = Arrays.asList(PetGender.values());
		petTypeList = Arrays.asList(PetType.values());
	}

	public void loadData() {
		petLazyData = new BaseLazyModel<Pet, Long>(getPetService());
	}

	public void showEditPanel() {
		selectedPet = new Pet();
		renderEditView = true;
		createOrEdit = true;
	}

	public void getBack() {
		renderEditView = false;
		createOrEdit = true;
	}

	public void add() {
		try {
			
			selectedPet.setImg(file);
			petService.save(selectedPet);

			Messages.create("REGISTRO").detail("Registro agregado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedPet = new Pet();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}

		selectedPet = new Pet();
	}

	public void prepareUpdate(boolean isCat) {
		createOrEdit = false;
		renderEditView = true;
	}

	public void update() {
		try {
			petService.save(selectedPet);
			Messages.create("REGISTRO").detail("Registro actualizado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedPet = new Pet();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
		selectedPet = new Pet();
	}

	public void delete(Pet pet) {
		petService.deleteOne(pet);
		Messages.create("REGISTRO").detail("Eliminado exitosamente").add();
	}

	public void upload(FileUploadEvent event) {
		try {
			
			file = IOUtils.toByteArray(event.getFile().getInputstream());
            //copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			event.getFile().getInputstream().close();
		} catch (IOException e) {
        	
        }
	}

	public void copyFile(String fileName, InputStream in) {
		try {

	        String contextPath = Faces.getRequest().getSession().getServletContext().getRealPath("");
	        contextPath = contextPath + "\\img\\";
	        
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(contextPath + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
