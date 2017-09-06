package org.grantharper.websecurity.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.grantharper.websecurity.domain.BankAccount;
import org.grantharper.websecurity.domain.Customer;
import org.grantharper.websecurity.service.BankAccountService;
import org.grantharper.websecurity.validator.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Profile("secure")
@Validated
public class SecureController {

	private static final Logger log = LoggerFactory.getLogger(SecureController.class);
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private CustomerValidator customerValidator;
	
	@Value("${input.validation.enabled}")
	private boolean inputValidationEnabled;

	@RequestMapping(value = "/customer/profile", params = { "firstName", "lastName" }, method = RequestMethod.GET)
	public String updateCustomerName(Model model, @Valid Customer customer, HttpServletRequest request, BindingResult bindingResult, HttpServletResponse response) {
		
		if(inputValidationEnabled){
			customerValidator.validate(customer, bindingResult);
			
			if(bindingResult.hasErrors()){
				return "customer-profile";
			}
		}
		
		log.debug("entered the param controller with firstName=" + customer.getFirstName() + " and lastName=" + customer.getLastName());

		String username = request.getUserPrincipal().getName();
		bankAccountService.updateCustomerName(username, customer.getFirstName(), customer.getLastName());
		return "redirect:/customer";
	}


	@RequestMapping(value = "/customer/account/{accountId}/deposit", method = RequestMethod.POST)
	public String performCustomerDeposit(Model model, @PathVariable("accountId") String accountId,
			@RequestParam("amount") Double amount) {
		log.debug("depositing into accountId=" + accountId + " amount=" + amount);
		
		bankAccountService.depositSecure(Long.valueOf(accountId), amount);
		return "redirect:/customer/account/" + accountId;
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String getEmployeePage(Model model) {
		List<Customer> customers = bankAccountService.retrieveAllCustomers();
		model.addAttribute("customers", customers);
		return "employee";
	}

	@Secured("ROLE_EMPLOYEE")
	@RequestMapping(value = "/sensitive", method = RequestMethod.GET)
	public void retrieveEmployeeDocument(Model model,
			HttpServletResponse response) throws IOException {

		OutputStream outputStream = null;
		response.setHeader("Content-Disposition", "attachment;filename=sensitive.txt");
		response.setContentType("text/plain");
		File file = new File("src/main/resources/sensitive.txt");
		response.setContentLengthLong(file.length());

		outputStream = response.getOutputStream();
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		byte[] byteArray = new byte[8];
		int length;
		while ((in != null) && ((length = in.read(byteArray)) != -1)) {
			outputStream.write(byteArray, 0, length);
		}

		in.close();
		outputStream.close();

	}
	
}
