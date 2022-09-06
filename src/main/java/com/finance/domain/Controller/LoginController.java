/**
 * 
 */
package com.finance.domain.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.finance.domain.Entity.User;
import com.finance.domain.Service.UserService;

/**
 * @author Dinesh Singh
 *
 */
@RestController
@RequestMapping(value = "/finance")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/authenticate")
	@ResponseBody
	public ResponseEntity<String> userAuthenticate(@RequestBody User userRequest){
		
		String data = userService.userAuthenticate(userRequest);
		return new ResponseEntity(data, HttpStatus.OK);
		
	}

}
