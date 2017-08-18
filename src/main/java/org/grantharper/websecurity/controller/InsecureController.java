package org.grantharper.websecurity.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Profile("insecure")
public class InsecureController {

	@RequestMapping(value = "/protected/{resourceId}", method = RequestMethod.GET)
	public String accessProtectedResource(Model model, @PathVariable("resourceId") String resourceId) {
		
		return "protected";
	}
	

}
