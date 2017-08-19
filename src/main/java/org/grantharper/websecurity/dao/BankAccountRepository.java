package org.grantharper.websecurity.dao;

import javax.transaction.Transactional;

import org.grantharper.websecurity.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	
	
}
