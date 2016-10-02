package com.org.web.config.security;

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

public class HttpSecurityConfiguration {

	public void onINit(@Observes  SecurityConfigurationEvent event){
		 
		SecurityConfigurationBuilder builder = event.getBuilder();
 
		 builder
         .http()
             .allPaths()
                 .authenticateWith()
                     .form()
                         .authenticationUri("/login.xhtml")
                         .loginPage("/login.xhtml")
                         .errorPage("/error.xhtml")
                         .restoreOriginalRequest()
             .forPath("/javax.faces.resource/*")
                 .unprotected()
             .forPath("/logout")
                 .logout()
                 .redirectTo("/index.xhtml")
             .forPath("/index.xhtml")
                 .unprotected();
		 
	}
	
	
	
}
