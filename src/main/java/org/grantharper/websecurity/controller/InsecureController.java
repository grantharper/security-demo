package org.grantharper.websecurity.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grantharper.websecurity.domain.BankAccount;
import org.grantharper.websecurity.domain.Customer;
import org.grantharper.websecurity.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Profile("insecure")
public class InsecureController {
	
	private static final Logger log = LoggerFactory.getLogger(InsecureController.class);

	@Autowired
	BankAccountService bankAccountService;

	@RequestMapping(value = "/protected/{resourceId}", method = RequestMethod.GET)
	public String accessProtectedResource(Model model, @PathVariable("resourceId") String resourceId) {

		return "protected";
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String getCustomerPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		String username = request.getUserPrincipal().getName();
		log.debug("customer method username=" + username);
		Customer customer = bankAccountService.retrieveCustomerByUsername(username);
		model.addAttribute("customer", customer);
		model.addAttribute("nameTest", customer.getFirstName());
		
		return "customer";
	}

	@RequestMapping(value = "/customer/account/{accountId}", method = RequestMethod.GET)
	public String getAccountPage(Model model, @PathVariable("accountId") String accountId) {
		BankAccount account = bankAccountService.retrieveBankAccountById(Long.valueOf(accountId));
		model.addAttribute("account", account);
		return "account";
	}

	@RequestMapping(value = "/customer", params = {"firstName", "lastName"}, method = RequestMethod.GET)
	public String updateCustomerName(Model model, HttpServletRequest request,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		log.debug("entered the param controller with firstName=" + firstName + " and lastName=" + lastName);
		String username = request.getUserPrincipal().getName();
		//bankAccountService.updateCustomerName(username, firstName, lastName);
		bankAccountService.updateCustomerNameInsecure(username, firstName, lastName);
		return "redirect:/customer";
	}
	
	@RequestMapping(value = "/customer/account/{accountId}/deposit", method = RequestMethod.POST)
	public String performCustomerDeposit(Model model, @PathVariable("accountId") String accountId, HttpServletRequest request){
		
		Double depositAmount = Double.valueOf(request.getParameter("amount"));
		log.debug("depositing into accountId=" + accountId + " amount=" + depositAmount);
		bankAccountService.deposit(Long.valueOf(accountId), depositAmount);
		return "redirect:/customer/account/" + accountId;
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String getEmployeePage(Model model) {
		List<Customer> customers = bankAccountService.retrieveAllCustomers();
		model.addAttribute("customers", customers);
		return "employee";
	}

	@RequestMapping(value = "/employee/customer/{customerId}", method = RequestMethod.GET)
	public String getEmployeeCustomerPage(Model model, @PathVariable("customerId") String customerId) {
		Customer customer = bankAccountService.retrieveCustomerById(Long.valueOf(customerId));
		model.addAttribute("customer", customer);
		return "customer";
	}

	@RequestMapping(value = "/employee/{documentId}", method = RequestMethod.GET)
	public void retrieveEmployeeDocument(Model model, @PathVariable("documentId") String documentId,
			HttpServletResponse response) throws IOException {

		if (documentId != null && documentId.equals("BC-0000123")) {

			OutputStream outputStream = null;
			response.setHeader("Content-Disposition", "attachment;filename=sensitive.txt");
			response.setContentType("text/plain");
			File file = new File("sensitive.txt");

			// TODO stream the file to the user
			outputStream = response.getOutputStream();
			outputStream.write(new byte[] {});
			outputStream.close();

		}

	}
	
	@RequestMapping(value = "/receive-hack", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> receiveHack(HttpServletRequest request){
		log.info("hack sent account details: " + request.getParameter("accountInfo"));
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
