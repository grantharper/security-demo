package org.grantharper.websecurity.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BankAccountInsecureDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void updateBalance(Long accountId, Double balance){
		
		StringBuilder queryBuilder = new StringBuilder("UPDATE bank_account SET balance=");
		queryBuilder.append(balance);
		queryBuilder.append(" WHERE account_id=");
		queryBuilder.append(accountId);
		queryBuilder.append(";");
		
		jdbcTemplate.execute(queryBuilder.toString());
	}
	
	//hack '; drop table customer; --
	//this will delete the customer table from the database
	//'; update bank_account set balance=999999999 where customer_id=1; --
	public void updateCustomerName(String firstName, String lastName, String username){
		StringBuilder queryBuilder = new StringBuilder("UPDATE customer SET first_name='");
		queryBuilder.append(firstName);
		queryBuilder.append("', last_name='");
		queryBuilder.append(lastName);
		queryBuilder.append("' WHERE username='");
		queryBuilder.append(username);
		queryBuilder.append("';");
		
		jdbcTemplate.execute(queryBuilder.toString());
	}
	
}
