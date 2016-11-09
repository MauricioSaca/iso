package com.org.web.pets.views;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;

import com.org.adoption.model.AdoptedPets;
import com.org.adoption.model.Postulant;
import com.org.adoption.model.PostulantAnswerEvaluation;
import com.org.adoption.service.AdoptedPetsService;
import com.org.adoption.service.PostulantService;
import com.org.core.model.enums.PostulantGender;
import com.org.core.model.enums.PostulantStatus;
import com.org.core.model.enums.ProcessStatus;
import com.org.security.enums.GroupsSecurityRolesNames;
import com.org.security.enums.RolesSecurityNames;
import com.org.security.identity.stereotype.Group;
import com.org.security.identity.stereotype.Role;
import com.org.security.identity.stereotype.User;
import com.org.util.web.BaseLazyModel;

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
	
	private transient BaseLazyModel<AdoptedPets, Long> adoptedPetsLazyData;
	private AdoptedPets selectedAdoptedPet;
	private List<ProcessStatus> processStatusList;
	private Postulant postulanteSession;
	private List<AdoptedPets> listaSolicitudes;
	
	@PostConstruct
	public void init() {
		selectedAdoptedPet = new AdoptedPets();
		this.loadData();
		processStatusList = Arrays.asList(ProcessStatus.values());
		
		User currentUser = (User) identity.getAccount();
		postulanteSession = postulantService.findByUser(currentUser);
		
		if(postulanteSession != null){
			listaSolicitudes = adoptedPetsService.findByAdoptante(postulanteSession);			
		}
		
	}

	public void loadData() {
		adoptedPetsLazyData = new BaseLazyModel<AdoptedPets, Long>(getAdoptedPetsService());
	}
	
	public void enProceso(){
		selectedAdoptedPet.setProcessStatus(ProcessStatus.ENPROCESO);
		adoptedPetsService.save(selectedAdoptedPet);
	}
	
	public void aprobar(){
		selectedAdoptedPet.setProcessStatus(ProcessStatus.APROBADO);
		adoptedPetsService.save(selectedAdoptedPet);
		Messages.create("SOLICITUD").detail("SOLICITUD APROBADA").add();
	}
	
	public void denegar(){
		selectedAdoptedPet.setProcessStatus(ProcessStatus.DENEGADO);
		adoptedPetsService.save(selectedAdoptedPet);
		Messages.create("SOLICITUD").detail("SOLICITUD DENEGADA").add();
	}
}
