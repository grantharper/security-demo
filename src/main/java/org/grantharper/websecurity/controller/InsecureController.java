package org.grantharper.websecurity.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.TextFileWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Profile("insecure")
public class InsecureController {

	private static final Logger log = LoggerFactory.getLogger(InsecureController.class);

	@Autowired
	BankAccountService bankAccountService;

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String getCustomerPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		populateCustomerDetails(model, request);

		return "customer";
	}

	private void populateCustomerDetails(Model model, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		log.debug("customer method username=" + username);
		Customer customer = bankAccountService.retrieveCustomerByUsername(username);
		model.addAttribute("customer", customer);
	}

	@RequestMapping(value = "/customer/account/{accountId}", method = RequestMethod.GET)
	public String getAccountPage(Model model, @PathVariable("accountId") String accountId) {
		BankAccount account = bankAccountService.retrieveBankAccountById(Long.valueOf(accountId));
		model.addAttribute("account", account);
		return "account";
	}

	@RequestMapping(value = "/customer/profile", method = RequestMethod.GET)
	public String displayCustomerProfile(Model model, HttpServletRequest request) {
		populateCustomerDetails(model, request);
		return "customer-profile";
	}

	@RequestMapping(value = "/customer/profile", params = { "firstName", "lastName" }, method = RequestMethod.GET)
	public String updateCustomerName(Model model, HttpServletRequest request) {
		String firstName = (String) request.getParameter("firstName");
		String lastName = (String) request.getParameter("lastName");
		log.debug("entered the param controller with firstName=" + firstName + " and lastName=" + lastName);
		String username = request.getUserPrincipal().getName();
		bankAccountService.updateCustomerNameInsecure(username, firstName, lastName);

		return "redirect:/customer";
	}

	@RequestMapping(value = "/customer/account/{accountId}/deposit", method = RequestMethod.POST)
	public String performCustomerDeposit(Model model, @PathVariable("accountId") String accountId,
			HttpServletRequest request) {
		log.debug("depositing into accountId=" + accountId + " amount=" + request.getParameter("amount"));
		Double depositAmount = Double.valueOf(request.getParameter("amount"));

		bankAccountService.depositInsecure(Long.valueOf(accountId), depositAmount);
		return "redirect:/customer/account/" + accountId;
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String getEmployeePage(Model model) {
		List<Customer> customers = bankAccountService.retrieveAllCustomers();
		model.addAttribute("customers", customers);
		return "employee-insecure";
	}

	@RequestMapping(value = "/employee/customer/{customerId}", method = RequestMethod.GET)
	public String getEmployeeCustomerPage(Model model, @PathVariable("customerId") String customerId) {
		Customer customer = bankAccountService.retrieveCustomerById(Long.valueOf(customerId));
		model.addAttribute("customer", customer);
		return "customer";
	}

	@RequestMapping(value = "/sensitive", method = RequestMethod.GET)
	public void retrieveEmployeeDocument(Model model, HttpServletResponse response) throws IOException {

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

	@RequestMapping(value = "/receive-hack", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> receiveHack(HttpServletRequest request) {
		log.info("hack sent account details: " + request.getParameter("accountInfo"));
		String accountInfo = request.getParameter("accountInfo");
		try {
			OutputStreamWriter os = new FileWriter("hack-output.txt", true);
			os.append(accountInfo);
			os.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
