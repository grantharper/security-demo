package org.grantharper.websecurity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="account_id")
	private Long accountId;
	
	@ManyToOne
	@Column(name="customer_id")
	private Long customerId;
	
	private Double balance;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
	
}
