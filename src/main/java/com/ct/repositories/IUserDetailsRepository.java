package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.ct.dao.UserDAO;

@Service
public interface IUserDetailsRepository extends MongoRepository<UserDAO, Integer> {

	public UserDAO findById(Integer id);

	public UserDAO findByEmail(String email);
	
	
	

}
