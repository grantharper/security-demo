package org.grantharper.websecurity.service;

import java.util.List;

import org.grantharper.websecurity.domain.BankAccount;
import org.grantharper.websecurity.domain.Customer;

public interface BankAccountService {

	Customer retrieveCustomerByUsername(String username);
	
	List<Customer> retrieveAllCustomers();
	
	Customer retrieveCustomerById(Long id);
	
	BankAccount retrieveBankAccountById(Long id);
	
	void updateCustomerName(String username, String firstName, String lastName);
	
	void updateCustomerNameInsecure(String username, String firstName, String lastName);
	
	void depositInsecure(Long accountId, Double depositAmount);
	
	void depositSecure(Long accountId, Double depositAmount);
	
	void closeAccount(Long accountId);
	
}
