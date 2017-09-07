package org.grantharper.websecurity.service;

import java.util.List;

import org.grantharper.websecurity.dao.BankAccountInsecureDao;
import org.grantharper.websecurity.dao.BankAccountRepository;
import org.grantharper.websecurity.dao.CustomerRepository;
import org.grantharper.websecurity.domain.BankAccount;
import org.grantharper.websecurity.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	BankAccountRepository accountRepo;
	
	@Autowired
	BankAccountInsecureDao insecureDao;
	
	@Override
	public Customer retrieveCustomerByUsername(String username) {
		Customer customer = customerRepo.findByUsername(username);
		return customer;
	}

	@Override
	public List<Customer> retrieveAllCustomers() {
		List<Customer> customers = customerRepo.findAll();
		for(Customer customer: customers){
			customer.setTotalAccountValue();
		}
		return customers;
	}

	@Override
	public Customer retrieveCustomerById(Long id) {
		Customer customer = customerRepo.findOne(id);
		return customer;
	}

	@Override
	public BankAccount retrieveBankAccountById(Long id) {
		BankAccount account = accountRepo.findOne(id);
		return account;
	}

	@Override
	public void updateCustomerName(String username, String firstName, String lastName) {
		Customer customer = customerRepo.findByUsername(username);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customerRepo.save(customer);
	}

	@Override
	public void depositInsecure(Long accountId, Double depositAmount) {
		BankAccount account = accountRepo.findOne(accountId);
		
		Double currentBalance = account.getBalance();
		currentBalance+=depositAmount;
		
		insecureDao.updateBalance(accountId, currentBalance);
	}
	
	@Override
	public void depositSecure(Long accountId, Double depositAmount) {
		BankAccount account = accountRepo.findOne(accountId);
		
		Double currentBalance = account.getBalance();
		currentBalance+=depositAmount;
		account.setBalance(currentBalance);
		accountRepo.save(account);
	}

	@Override
	public void updateCustomerNameInsecure(String username, String firstName, String lastName) {
		insecureDao.updateCustomerName(firstName, lastName, username);
	}

  @Override
  public void closeAccount(Long accountId)
  {
    accountRepo.delete(accountId);
  }

}
