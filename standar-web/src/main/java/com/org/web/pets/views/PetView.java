package com.org.web.pets.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.org.adoption.model.Cat;
import com.org.adoption.model.Dog;
import com.org.adoption.service.CatService;
import com.org.adoption.service.DogService;
import com.org.core.model.enums.PetGender;
import com.org.core.model.enums.PetOrigin;
import com.org.core.model.enums.PetStatus;
import com.org.util.web.BaseLazyModel;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PetView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 456787809L;
	
	@Inject
	private transient CatService catService;
	
	@Inject
	private transient DogService dogService;
	
	private transient BaseLazyModel<Dog, Long> dogLazyData;
	private transient BaseLazyModel<Cat, Long> catLazyData;
	private Cat selectedCat;
	private Dog selectedDog;
	private List<PetOrigin> petOriginList;
	private List<PetStatus> petStatusList;
	private List<PetGender> petGenderList;
	
	/**
	 * True for cats, False for Dogs
	 */
	private boolean catDogFlag;
	
	@PostConstruct
	public void init(){
		selectedCat = new Cat();
		this.loadData();
		petOriginList = Arrays.asList(PetOrigin.values());
		petStatusList = Arrays.asList(PetStatus.values());
		petGenderList = Arrays.asList(PetGender.values());
	}
	
	public void loadData(){
		dogLazyData = new BaseLazyModel<Dog, Long>(getDogService());
		catLazyData = new BaseLazyModel<Cat, Long>(getCatService());
	}
	
	public CatService getCatService() {
		return catService;
	}

	public DogService getDogService() {
		return dogService;
	}

	public BaseLazyModel<Dog, Long> getDogLazyData() {
		return dogLazyData;
	}

	public BaseLazyModel<Cat, Long> getCatLazyData() {
		return catLazyData;
	}

	public void registerPet() {
		if (catDogFlag) {
			catService.save(selectedCat);
		} else {
			dogService.save(selectedDog);
		}
	}
	
	public void updatePet(){
		
	}

	public void deletePet(){
		
	}
}
