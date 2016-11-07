package com.org.web.config;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import com.org.adoption.service.PetImagesService;
import com.org.adoption.service.PetService;

@Named
@ApplicationScoped
public class ImageBean {
	
	@EJB
	private PetService service;
	
	@EJB
	private PetImagesService petImagesService;
	
    public byte[] getBytes(Long id) {
        return service.findOne(id).getImg();
    }
    
    public byte[] getBytesImg(Long id) {
    	return petImagesService.findOne(id).getImg();
    }
}
