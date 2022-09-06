/**
 * 
 */
package com.finance.domain.oracleRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.domain.Entity.User;

/**
 * @author Dinesh Singh
 *
 */
public interface OUserRepository  extends JpaRepository<User, Long>{

	User findByUsernameANDPassword(String username, String password);
	
}
