package org.grantharper.websecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(value = Exception.class)
	public String errorPage(Model model, Exception exception){
		
		log.error(exception.getMessage(), exception);
		
		return "custom-error";
	}
	
}
