package com.org.web.pets.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.adoption.model.AdoptedPets;
import com.org.adoption.model.Pet;
import com.org.adoption.model.Postulant;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.service.AdoptedPetsService;
import com.org.adoption.service.PetService;
import com.org.adoption.service.PostulantQuestionsService;
import com.org.adoption.service.PostulantService;
import com.org.core.model.enums.PetStatus;
import com.org.core.model.enums.ProcessStatus;
import com.org.security.identity.model.UserTypeEntity;
import com.org.security.identity.stereotype.User;
import com.org.util.web.BaseLazyModel;
import com.org.util.web.EmailUtil;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PetAdopterView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456787809L;

	@Inject
	private Identity identity;

	@Inject
	private transient AdoptedPetsService adoptedPetsService;

	@Inject
	private transient PostulantService postulantService;

	@Inject
	private transient PostulantQuestionsService postulantQuestionsService;
	
	@Inject
	private transient PetService petService;

	private transient BaseLazyModel<AdoptedPets, Long> adoptedPetsLazyData;
	private AdoptedPets selectedAdoptedPet;
	private List<ProcessStatus> processStatusList;
	private Postulant postulanteSession;
	private List<AdoptedPets> listaSolicitudes;
	private List<PostulantAnswerEvaluation> quiz;
	private boolean renderDetail = false;
	private UserTypeEntity userTypeEntity;
	
	@PostConstruct
	public void init() {
		selectedAdoptedPet = new AdoptedPets();
		this.loadData();
		processStatusList = Arrays.asList(ProcessStatus.values());

		User currentUser = (User) identity.getAccount();
		postulanteSession = postulantService.findByUser(currentUser);
		userTypeEntity = new UserTypeEntity();
		userTypeEntity.setId(currentUser.getId());
		
		if (postulanteSession != null) {
			listaSolicitudes = adoptedPetsService.findByAdoptante(postulanteSession);
		}

	}

	public void loadData() {
		adoptedPetsLazyData = new BaseLazyModel<AdoptedPets, Long>(getAdoptedPetsService());
	}

	public void enProceso() {
		if (selectedAdoptedPet != null) {
			quiz = postulantQuestionsService.findQuestionsAnswerByPostulant(selectedAdoptedPet.getPostulant());
			selectedAdoptedPet.setProcessStatus(ProcessStatus.ENPROCESO);
			selectedAdoptedPet.setFechaEnProceso(new Date());
			selectedAdoptedPet.setUserTypeEntity(userTypeEntity);
			
			adoptedPetsService.save(selectedAdoptedPet);
			renderDetail = true;
		}
	}

	public void aprobar() {
		try {
			selectedAdoptedPet.setProcessStatus(ProcessStatus.APROBADO);
			selectedAdoptedPet.setUserTypeEntity(userTypeEntity);
			selectedAdoptedPet.setFechaAprobacion(new Date());
			adoptedPetsService.save(selectedAdoptedPet);
			
			Pet pet = selectedAdoptedPet.getPet();
			pet.setPetStatus(PetStatus.ADOPTED);
			
			petService.save(pet);
			
			String message = "Su solicitud de adopcion para la mascota: "
					+ pet.getPetName() + " ha sido aprobado, por favor acercarse a nuestras instalaciones.";
			EmailUtil.sendMail("SOLICITUD APROBADA", message, selectedAdoptedPet.getPostulant().getEmail());
			
			renderDetail = false;
			Messages.create("SOLICITUD").detail("SOLICITUD APROBADA").add();
			
		} catch (Exception e) {
			Messages.create("OPS").detail("Algo ocurrio").error().add();
		}
	}

	public void denegar() {
		try {
			selectedAdoptedPet.setProcessStatus(ProcessStatus.DENEGADO);
			selectedAdoptedPet.setUserTypeEntity(userTypeEntity);
			selectedAdoptedPet.setFechaAprobacion(new Date());
			adoptedPetsService.save(selectedAdoptedPet);
			renderDetail = false;
			String message = "Su solicitud de adopcion para la mascota: "
					+ selectedAdoptedPet.getPet().getPetName() + " ha sido denegada. Consulte con nuestros asesores para mas informacion";
			EmailUtil.sendMail("SOLICITUD DENEGADA", message, selectedAdoptedPet.getPostulant().getEmail());
			Messages.create("SOLICITUD").detail("SOLICITUD DENEGADA").add();			
		} catch (Exception e) {
			Messages.create("OPS").detail("Algo ocurrio").error().add();
		}
	}
}
