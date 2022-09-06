/**
 * 
 */
package com.finance.domain.sqlRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.domain.Entity.User;

/**
 * @author Dinesh Singh
 *
 */

public interface MUserRepository extends JpaRepository<User, Long>{

	User findByUsernameANDPassword(String username, String password);

}
