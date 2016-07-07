package com.ct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ct.dao.UserDAO;
import com.ct.model.User;
import com.ct.repositories.IUserDetailsRepository;
import com.ct.services.UserService;

@Service
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	IUserDetailsRepository userDetailsRepo;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String userEmail = authentication.getName();
		System.out.println("userEmail in authProvider: "+userEmail );
		String password = authentication.getCredentials().toString();
		if (userEmail != null && password != null) {
			UserDAO userDAO = getUserDAO(userEmail);
			System.out.println("userDAO in authProvider:"+userDAO.getEmail() );
			if (!userDAO.getPassword().equals(password)) {
				throw new BadCredentialsException("Password did not match");
			}
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(userEmail,
				password);
		return auth;
	}

	private UserDAO getUserDAO(String userName) {
		UserDAO userDAO = userDetailsRepo.findByEmail(userName);
		if(userDAO!=null)
			return userDAO;
		else{
			return null;
		}
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);

	}

}
