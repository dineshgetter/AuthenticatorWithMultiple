/**
 * 
 */
package com.finance.domain.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.domain.Entity.User;
import com.finance.domain.Service.UserService;
import com.finance.domain.oracleRepo.OUserRepository;
import com.finance.domain.sqlRepo.MUserRepository;
/**
 * @author Dinesh Singh
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	MUserRepository MuserRepository;
	
	@Autowired
	OUserRepository OuserRepository;

	@Override
	public String userAuthenticate(User userRequest) {
		// TODO Auto-generated method stub
		String authenticatedUser = "";
		
		if(MuserRepository.findByUsernameANDPassword(userRequest.getUsername(), userRequest.getPassword())  != null) {
			authenticatedUser = "Hello "+ MuserRepository.findByUsernameANDPassword(userRequest.getUsername(), userRequest.getPassword()).getUsername()+ "from MySQL Database";
		}

		if(OuserRepository.findByUsernameANDPassword(userRequest.getUsername(), userRequest.getPassword())  != null) {
			authenticatedUser = "Hello "+ OuserRepository.findByUsernameANDPassword(userRequest.getUsername(), userRequest.getPassword()).getUsername()+ "from Oracle Database";
		}
		
		return authenticatedUser;
	}
	

}
