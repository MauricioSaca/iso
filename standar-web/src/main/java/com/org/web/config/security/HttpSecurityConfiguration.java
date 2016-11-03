package com.org.web.config.security;

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;
import org.picketlink.http.AuthenticationRequiredException;

import com.org.security.enums.GroupsSecurityRolesNames;
/**
 * 
 * @author saca
 * Initial Security Path Configurations
 */
public class HttpSecurityConfiguration {

	public void configureHttpSecurity(@Observes SecurityConfigurationEvent event){
		SecurityConfigurationBuilder builder = event.getBuilder();
		
		builder
         .http()
             .forPath("/*.xhtml")
                 .authenticateWith()
                     .form()
                         .authenticationUri("/login.xhtml")
                         .loginPage("/login.xhtml")
                         .errorPage("/error.xhtml")
                         .restoreOriginalRequest()
             .forPath("/logout")
             	.logout()
             	.redirectTo("/login.xhtml")
             .forPath("/mascotas/*") //postulant
             	.authorizeWith()
             		.group(GroupsSecurityRolesNames.POSTULANDS.getCode())
             			.redirectTo("/errors/access-denied.xhtml")
             				.whenException(AuthenticationRequiredException.class)
              .forPath("/postulante/*")
              	.authorizeWith()
              		.group(GroupsSecurityRolesNames.POSTULANDS.getCode())
              			.redirectTo("/errors/access-denied.xhtml")
              				.whenException(AuthenticationRequiredException.class)
              .forPath("/security/*") //Admins
              	.authorizeWith()
              		.group(GroupsSecurityRolesNames.ADMINS.getCode())
              			.redirectTo("/errors/access-denied.xhtml")
              				.whenException(AuthenticationRequiredException.class)
              .forPath("/indicadores_metas/*") //Manager
              	.authorizeWith()
              		.group(GroupsSecurityRolesNames.MANAGERS.getCode())
              			.redirectTo("/errors/access-denied.xhtml")
              				.whenException(AuthenticationRequiredException.class)
              .forPath("/validacion_postulante/*") //Organizacional
              	.authorizeWith() 
              		.group(GroupsSecurityRolesNames.ORGANIZERS.getCode())
              			.redirectTo("/errors/access-denied.xhtml")
              				.whenException(AuthenticationRequiredException.class)
//              .forPath("/index.xhtml")
//                .redirectTo("/login.xhtml")
             .forPath("/javax.faces.resource/*")
                 .unprotected();	 
	}	
}
