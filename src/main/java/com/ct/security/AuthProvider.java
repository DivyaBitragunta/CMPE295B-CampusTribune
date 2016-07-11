package com.ct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ct.dao.UserDAO;
import com.ct.repositories.IUserDetailsRepository;

@Service
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	IUserDetailsRepository userDetailsRepo;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String userId = authentication.getName();
		System.out.println("userId in authProvider: "+userId );
		String password = authentication.getCredentials().toString();
		System.out.println("userId-password in authProvider: "+password );
		if (userId != null && password != null) {
			UserDAO userDAO = getUserDAO(userId);
			if(userDAO!=null){
				System.out.println("userDAO in authProvider:"+userDAO.getEmail() );
				System.out.println("userDAO password----"+userDAO.getPassword());
				if (!userDAO.getPassword().equals(password)) {
					throw new BadCredentialsException("Password did not match!");
				}
			}
			else{
				throw new BadCredentialsException("UserId did not match any record!");
			}
			
		}
		System.out.println("Passwords matched");
		Authentication auth = new UsernamePasswordAuthenticationToken(userId,
				password);
		return auth;
	}

	private UserDAO getUserDAO(String userName) {
		UserDAO userDAO = userDetailsRepo.findById(userName);
		if(userDAO!=null){
			System.out.println("In getUserDAO");
			System.out.println("email:"+userDAO.getEmail());
			System.out.println("password:"+userDAO.getPassword());
			System.out.println("firstName"+userDAO.getFirstName());
			System.out.println("lastName"+userDAO.getLastName());
			return userDAO;
		}
		else{
			return null;
		}
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);

	}

}
