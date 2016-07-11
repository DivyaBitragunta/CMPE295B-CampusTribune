package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.ct.dao.UserDAO;

@Service
public interface IUserDetailsRepository extends MongoRepository<UserDAO, String> {

	public UserDAO findById(String id);

	public UserDAO findByEmail(String email);

	
	
	

}
