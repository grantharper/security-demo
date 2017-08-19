package org.grantharper.websecurity.dao;

import javax.transaction.Transactional;

import org.grantharper.websecurity.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Customer findByUsername(String username);
}
