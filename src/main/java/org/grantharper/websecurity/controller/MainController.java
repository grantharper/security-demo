package org.grantharper.websecurity.controller;

import javax.servlet.http.HttpServletRequest;

import org.grantharper.websecurity.domain.BankAccount;
import org.grantharper.websecurity.domain.Customer;
import org.grantharper.websecurity.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController
{

  private static final Logger log = LoggerFactory.getLogger(MainController.class);

  @Autowired
  BankAccountService bankAccountService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String accessProtectedResource(Model model)
  {
    return "index";
  }

  @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
  public String getAccessDenied(Model model, HttpServletRequest request)
  {
    String referrer = request.getHeader("Referer");
    model.addAttribute("referrerUrl", referrer);

    return "access-denied";
  }

  @RequestMapping(value = "/employee/customer/{customerId}/account/{accountId}", method = RequestMethod.GET)
  public String getEmployeeCustomerAccountPage(Model model, @PathVariable("accountId") String accountId,
      @PathVariable("customerId") String customerId)
  {
    BankAccount account = bankAccountService.retrieveBankAccountById(Long.valueOf(accountId));
    model.addAttribute("account", account);
    Customer customer = bankAccountService.retrieveCustomerById(Long.valueOf(customerId));
    model.addAttribute("customer", customer);
    return "account";
  }

}
