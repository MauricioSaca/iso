package com.org.adoption.validator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.org.adoption.service.PostulantService;

@Named
public class EmailValidator implements Validator {

	@EJB
	private PostulantService service;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		String email = (String) value;

		FacesMessage msg = new FacesMessage("Email ya existe",
						"Favor usar oro");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		
		if (service.existMail(email)) {
			throw new ValidatorException(msg);
		}
	}

}
