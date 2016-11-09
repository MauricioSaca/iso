package com.org.web.pets.views;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;
import org.primefaces.event.FileUploadEvent;

import com.org.adoption.model.AdoptedPets;
import com.org.adoption.model.MedicalControl;
import com.org.adoption.model.Pet;
import com.org.adoption.model.PetImages;
import com.org.adoption.model.Postulant;
import com.org.adoption.service.AdoptedPetsService;
import com.org.adoption.service.MedicalControlService;
import com.org.adoption.service.PetImagesService;
import com.org.adoption.service.PetService;
import com.org.adoption.service.PostulantService;
import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;
import com.org.core.model.enums.PetType;
import com.org.core.model.enums.ProcessStatus;
import com.org.security.identity.stereotype.User;
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
	private Identity identity;
	
	@Inject
	private transient PetService petService;

	@Inject
	private transient PetImagesService petImagesService;

	@Inject
	private transient AdoptedPetsService adoptedPetsService;
	
	@Inject
	private transient PostulantService postulantService;
	
	@Inject
	private transient MedicalControlService medicalControlService;

	private transient BaseLazyModel<Pet, Long> petLazyData;
	private Pet selectedPet;
	private MedicalControl selectedMedicalControl;
	private List<PetOrigin> petOriginList;
	private List<PetStatus> petStatusList;
	private List<PetGender> petGenderList;
	private List<PetType> petTypeList;
	private Postulant postulante;
	private byte[] file;
	private List<byte[]> files;
	/**
	 * TRUE for create FALSE for edit
	 */
	private boolean createOrEdit;
	private boolean flagFirst = true;
	/**
	 * TRUE for add/edit, FALSE for list view
	 */
	private boolean renderEditView = false;
	private boolean renderFichaDetail = false;

	@PostConstruct
	public void init() {
		selectedPet = new Pet();
		selectedMedicalControl = new MedicalControl();
		files = new ArrayList<byte[]>();
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
		files = new ArrayList<byte[]>();
		renderEditView = true;
		createOrEdit = true;
		flagFirst = true;
	}

	public void getBack() {
		renderEditView = false;
		createOrEdit = true;
	}

	public void add() {
		List<PetImages> imagesList = new ArrayList<PetImages>();
		try {

			if (file == null || file.length == 0) {
				Messages.create("IMAGEN").detail("Cargue imagen").error().add();
			} else {
				selectedPet.setImg(file);
				petService.save(selectedPet);

				if (!files.isEmpty()) {

					for (byte[] bytee : files) {
						PetImages images = new PetImages();
						images.setPet(selectedPet);
						images.setImg(bytee);

						imagesList.add(images);
					}
				}

				// guardamos las imagenes
				petImagesService.save(imagesList);

				Messages.create("REGISTRO").detail("Registro agregado exitosamente").add();
				renderEditView = false;
				selectedPet = new Pet();
			}
		} catch (Exception e) {
			selectedPet = new Pet();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
	}

	public void prepareUpdate() {
		createOrEdit = false;
		renderEditView = true;
		flagFirst = true;
		file = selectedPet.getImg();
	}

	public void update() {
		try {
			selectedPet.setImg(file);
			petService.save(selectedPet);
			Messages.create("REGISTRO").detail("Registro actualizado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedPet = new Pet();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
		selectedPet = new Pet();
	}

	public void delete() {
		try {
			if(selectedPet != null){
				petService.deleteOne(selectedPet);
				Messages.create("REGISTRO").detail("Eliminado exitosamente").add();			
			}
		} catch (Exception e) {
			Messages.create("ERROR").detail("No se puede eliminar registro con dependencias").error().add();
		}
	}

	public void upload(FileUploadEvent event) {
		try {

			if (flagFirst) {
				file = IOUtils.toByteArray(event.getFile().getInputstream());
				flagFirst = false;
			}

			files.add(IOUtils.toByteArray(event.getFile().getInputstream()));

			event.getFile().getInputstream().close();
		} catch (IOException e) {

		}
	}

	public List<PetImages> getGalery() {
		if (selectedPet != null && selectedPet.getId() != null) {

			// list.add(new PetImages(selectedPet, selectedPet.getImg()));
			return petService.findImagesGallery(selectedPet);
		}
		return new ArrayList<>();
	}

	public void seeFicha() {
		renderFichaDetail = true;
	}

	public void getBackAdopta() {
		renderFichaDetail = false;
	}

	/**
	 * 
	 */
	public void solicitarProceso(){
		AdoptedPets adoptedPets = null;
		
		User currentUser = (User) identity.getAccount(); //agarra usuario de la seguridad
		
		if(postulante == null){
			postulante = postulantService.findByUser(currentUser);			
		}
		
		adoptedPets = adoptedPetsService.findByPostAndPet(postulante, selectedPet);
		
		if (adoptedPets == null){
			adoptedPets = new AdoptedPets();
			
			adoptedPets.setPet(selectedPet);
			adoptedPets.setPostulant(postulante);
			adoptedPets.setProcessStatus(ProcessStatus.SOLICITADO);
			
			adoptedPetsService.save(adoptedPets);
			Messages.create("PROCESO").detail("PROCESO DE ADOPCION SOLICITADO").add();
			renderFichaDetail = false;
		} else {
			Messages.create("PROCESO").detail("YA HA SOLICITADO PROCESO PARA ESTA MASCOTA").error().add();
		}
		
	}
	
	public void prepareMedicalControl(){
		selectedMedicalControl = medicalControlService.findByPet(selectedPet);
		if(selectedMedicalControl == null){
			selectedMedicalControl = new MedicalControl();
		}
	}
	
	public void saveMedicalControl(){
		selectedMedicalControl.setPet(selectedPet);
		medicalControlService.save(selectedMedicalControl);
		Messages.create("REGISTRO MEDICO").detail("Guardado exitosamente").error().add();
		selectedMedicalControl = new MedicalControl();
	}

}
