package org.grantharper.websecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class ExceptionController {

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(value = Exception.class)
	public String errorPage(Model model, Exception exception){
		
		log.error(exception.getMessage(), exception);
		
		return "custom-error";
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public String accessDeniedError(Exception exception){
	  log.error(exception.getMessage(), exception);
	  return "access-denied";
	}
	
}
