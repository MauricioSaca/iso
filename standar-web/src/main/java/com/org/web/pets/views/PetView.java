package com.org.web.pets.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

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
	 * TRUE for create FALSE for edit
	 */
	private boolean createOrEdit;
	
	/**
	 * True for cats, False for Dogs
	 */
	private boolean catDogFlag;
	
	/**
	 * TRUE for add/edit, FALSE for list view
	 */
	private boolean renderEditView = false;
	
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

	public void showEditPanel(){
		renderEditView = true;
		createOrEdit = true;
	}
	
	public Cat getCatFromDog(Dog dog){
		Cat cat = new Cat();
		cat.setId(dog.getId());
		cat.setCatName(dog.getDogName());
		cat.setBreed(dog.getBreed());
		cat.setAge(dog.getAge());
		cat.setWeight(dog.getWeight());
		cat.setHeight(dog.getHeight());
		cat.setBehavior(dog.getBehavior());
		cat.setDescription(dog.getDescription());
		cat.setPetOrigin(dog.getPetOrigin());
		cat.setPetStatus(dog.getPetStatus());
		cat.setPetGender(dog.getPetGender());
		return cat;
	}
	
	public Dog getDogFromCat(Cat cat){
		Dog dog = new Dog();
		
		if(cat.getId() != null){
			dog.setId(cat.getId());			
		}
		
		dog.setDogName(cat.getCatName());
		dog.setBreed(cat.getBreed());
		dog.setAge(cat.getAge());
		dog.setWeight(cat.getWeight());
		dog.setHeight(cat.getHeight());
		dog.setBehavior(cat.getBehavior());
		dog.setDescription(cat.getDescription());
		dog.setPetOrigin(cat.getPetOrigin());
		dog.setPetStatus(cat.getPetStatus());
		dog.setPetGender(cat.getPetGender());
		return dog;
	}
	
	public void registerPet() {
		try {
			
			if (catDogFlag) {
				catService.save(selectedCat);
			} else {
				selectedDog = getDogFromCat(selectedCat);			
				dogService.save(selectedDog);
			}
			Messages.create("REGISTRO").detail("Registro agregado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedCat = new Cat();
			selectedDog = new Dog();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
		
		selectedCat = new Cat();
		selectedDog = new Dog();
	}
	
	public void prepareUpdate(boolean isCat){
		createOrEdit = false;
		renderEditView = true;
		selectedCat = !isCat ? getCatFromDog(selectedDog) : selectedCat;
		catDogFlag = !isCat ? false : true;
	}
	
	public void updatePet() {
		try {
			if (catDogFlag) {
				catService.save(selectedCat);
			} else {
				selectedDog = getDogFromCat(selectedCat);
				dogService.save(selectedDog);
			}

			Messages.create("REGISTRO").detail("Registro actualizado exitosamente").add();
			renderEditView = false;
		} catch (Exception e) {
			selectedCat = new Cat();
			selectedDog = new Dog();
			Messages.create("EXCEPTION").detail("ERROR: " + e.getMessage()).error().add();
		}
		selectedCat = new Cat();
		selectedDog = new Dog();
	}

	public void deleteCat(Cat cat){
		catService.deleteOne(cat);
		Messages.create("REGISTRO").detail("Eliminado exitosamente").add();
	}
	
	public void deleteDog(Dog dog){
		dogService.deleteOne(dog);
		Messages.create("REGISTRO").detail("Eliminado exitosamente").add();
	}
}
