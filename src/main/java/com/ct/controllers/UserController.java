package com.ct.controllers;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ct.dao.UserDAO;
import com.ct.model.User;
import com.ct.services.UserService;
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> signUp(@RequestBody UserDAO newUserDAO) throws AuthenticationException {
		System.out.println("Backend call worked!");
		User userCreated=userService.createUser(newUserDAO);
		if (userCreated != null)
			return new ResponseEntity<User>(userCreated, HttpStatus.CREATED);
		else
			throw new AuthenticationException("Error creating new user");
		
	}
	
	@RequestMapping(value="/front-page/{userId}", method = RequestMethod.GET, produces ="application/json")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId ) {
	  if(userService.isValid(userId))
		  return new ResponseEntity<User>(userService.getUserDetails(userId), HttpStatus.OK);
	  else{
		  return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	  }
    }
	
	@RequestMapping(value="/user-profile/{userId}", method = RequestMethod.POST, produces="application/json", consumes= "application/json")
	public ResponseEntity<UserDAO> updateUserSubscription(@PathVariable("userId") String userId, @RequestBody UserDAO updateUserDAO){
		if(userService.isValid(userId))
			return new ResponseEntity<UserDAO>(userService.updateUserSubscription(updateUserDAO, userId), HttpStatus.OK);
		else
			return new ResponseEntity<UserDAO>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<User> authenticateUser() {
		User user = userService.getAuthenticatedUser();
		if (user != null)
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	/*@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<User> logoutUser () {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	}*/

}
