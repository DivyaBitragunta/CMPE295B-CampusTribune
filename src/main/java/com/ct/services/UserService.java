package com.ct.services;

import java.util.Random;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.dao.UserDAO;
import com.ct.model.User;
import com.ct.repositories.IUserDetailsRepository;
import com.ct.repositories.IUserRepository;
import com.ct.security.AuthHelper;

@Service
public class UserService {
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private IUserDetailsRepository userDetailsRepo;
	
	@Autowired
	private AuthHelper authHelper;
	
	
	private Integer generateId(){
		Random r = new Random();
		return r.nextInt(9000) + 1000; 
	}
	
	public User createUser(UserDAO newUserDAO) {
		UserDAO userDAO = new UserDAO();
		User user=new User();
		Integer id = generateId();
		while(userDetailsRepo.exists(id))
			id = generateId();
		userDAO.setId(id);
		userDAO.setName(newUserDAO.getName());
		DateTime dt = new DateTime(DateTimeZone.UTC);		
		userDAO.created_at =  dt.toString(ISODateTimeFormat.dateTime().withZoneUTC());
		userDAO.setPassword(newUserDAO.getPassword()); 
		userDAO.setEmail(newUserDAO.getEmail());
		if(userDetailsRepo.save(userDAO)!=null){
			
			user.setId(userDAO.getId());
			user.setEmail(userDAO.getEmail());
			user.setName(userDAO.getName());
			user.setToken(null);
			
		}
		return user;
	}

	public boolean isValid(int userId) {
		if(!(userDetailsRepo.exists(userId)))
			return false;
		return true;
	}

	public UserDAO getUserDetails(int userId) {
		UserDAO user=new UserDAO();
		if(isValid(userId)){
			user=userDetailsRepo.findOne(userId);			
		}			
		return user;
	}

	

	private String createAuthToken() {
		String token = Base64.encodeBase64String(UUID.randomUUID().toString()
				.getBytes());
		authHelper.saveToken(token);
		return token;
	}

	public User getAuthenticatedUser() {
		String userEmail = authHelper.getUsername();
		String token = createAuthToken();
		UserDAO userDAO=userDetailsRepo.findByEmail(userEmail);
		User user=new User();
		user.setId(userDAO.getId());
		user.setEmail(userDAO.getEmail());
		user.setName(userDAO.getName());
		user.setToken(token);
		return user;

	}

}
