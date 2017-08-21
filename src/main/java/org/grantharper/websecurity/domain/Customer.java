package org.grantharper.websecurity.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="first_name", length=1000)
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	private String email;
	
	private String username;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customerId")
	private Set<BankAccount> accounts;

	@Transient
	private Double totalAccountValue;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getTotalAccountValue() {
		return totalAccountValue;
	}

	public void setTotalAccountValue() {
		Double accountValue = 0.0;
		for(BankAccount account : this.accounts){
			accountValue += account.getBalance();
		}
		this.totalAccountValue = accountValue;
	}
	
	
	
}
