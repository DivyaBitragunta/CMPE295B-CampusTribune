package com.ct.services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.dao.EventDAO;
import com.ct.dao.PostDAO;
import com.ct.dao.UserDAO;
import com.ct.mail.Mail;
import com.ct.model.Event;
import com.ct.model.User;
import com.ct.repositories.IEventRepository;
import com.ct.repositories.IPostRepository;
import com.ct.repositories.IUserDetailsRepository;
import com.ct.security.AuthHelper;

@Service
public class UserService {
	
	@Autowired
	private IUserDetailsRepository userDetailsRepo;
	
	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IEventRepository eventRepo;

	@Autowired
	private AuthHelper authHelper;
	
	@Autowired
	private EventService eventService;

	Mail mail= new Mail();

	private Integer generateId(){
		Random r = new Random();
		return r.nextInt(900) + 100; 
	}

	public User createUser(UserDAO newUserDAO) {
		
		
		UserDAO userDAO = new UserDAO();
		User user=new User();
		StringBuilder str= new StringBuilder();
		str.append(newUserDAO.getFirstName().substring(0,2));
		str.append(newUserDAO.getLastName().substring(0,2));
		Integer id = generateId();
		String userId=str.toString()+id;
		while(userDetailsRepo.exists(userId)){
			id = generateId();
			userId=userId.substring(0, userId.length()-3);
			userId=userId+id;			
		}
		userDAO.setId(userId);
		userDAO.setFirstName(newUserDAO.getFirstName());
		userDAO.setLastName(newUserDAO.getLastName());
		DateTime dt = new DateTime(DateTimeZone.UTC);		
		userDAO.created_at =  dt.toString(ISODateTimeFormat.dateTime().withZoneUTC());
		userDAO.setPassword(newUserDAO.getPassword()); 
		userDAO.setEmail(newUserDAO.getEmail());
		userDAO.setSendNotifications(true);
		userDAO.setSendRecommendations(true);
		userDAO.university= newUserDAO.university;
		ArrayList<String> defaultSubscriptionList= new ArrayList<String>();
		defaultSubscriptionList.add("Administration");
		defaultSubscriptionList.add("Sports");
		userDAO.setSubscriptionList(defaultSubscriptionList);
		if(userDetailsRepo.save(userDAO)!=null){			
			user.setId(userDAO.getId());
			user.setEmail(userDAO.getEmail());
			user.setFirstName(userDAO.getFirstName());
			user.setLastName(userDAO.getLastName());
			user.setUniversity(userDAO.university);
			user.subscriptionList=userDAO.getSubscriptionList();
			user.setToken(null);
		}
		mail.sendEmail(user.getEmail(), user.getId());
		return user;

	}

	public boolean isValid(String userId) {
		if(!(userDetailsRepo.exists(userId)))
			return false;
		return true;
	}

	public UserDAO getUserDetails(String userId) {
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
		String userId = authHelper.getUsername();
		String token = createAuthToken();
		UserDAO userDAO=userDetailsRepo.findById(userId);
		if(userDAO!=null){
			User user=new User();
			user.setId(userDAO.getId());
			user.setEmail(userDAO.getEmail());
			user.setFirstName(userDAO.getFirstName());
			user.setLastName(userDAO.getLastName());
			user.setUniversity(userDAO.university);
			user.setToken(token);
			user.setIsNotifyFlag(userDAO.getSendNotifications());
			user.setIsRecommendFlag(userDAO.getSendRecommendations());
			user.setPostList((ArrayList<PostDAO>) postRepo.findAll());
			user.setEventList((ArrayList<Event>) eventService.viewEvents());
			user.subscriptionList=userDAO.getSubscriptionList();
			return user;
		}
		else{
			System.out.println("Unable to retrieve user from DB!!");
			return null;
		}

	}

	public UserDAO updateUserSubscription(UserDAO updateUserDAO, String userId) {
		UserDAO user=new UserDAO();
		System.out.println("In update user SUBSCRIPTION");
		user=userDetailsRepo.findOne(userId);
		user.setSendNotifications(updateUserDAO.getSendNotifications());
		user.setSendRecommendations(updateUserDAO.getSendRecommendations());
		user.setSubscriptionList(updateUserDAO.getSubscriptionList());
		return userDetailsRepo.save(user);
	}

}
