package org.grantharper.websecurity.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String accessProtectedResource(Model model) {
		return "index";
	}
	
	@RequestMapping(value="/access-denied", method=RequestMethod.GET)
  public String getAccessDenied(Model model, HttpServletRequest request){
    String referrer = request.getHeader("Referer");
    model.addAttribute("referrerUrl", referrer);
    
    return "access-denied";
  }
	
}
