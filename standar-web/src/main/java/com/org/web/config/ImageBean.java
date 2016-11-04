package com.org.web.config;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import com.org.adoption.service.PetService;

@Named
@ApplicationScoped
public class ImageBean {
	
	@EJB
	private PetService service;
	
    public byte[] getBytes(Long id) {
        return service.findOne(id).getImg();
    }
}
