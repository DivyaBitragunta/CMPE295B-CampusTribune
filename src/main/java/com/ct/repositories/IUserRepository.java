package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.model.User;

public interface IUserRepository extends MongoRepository<User, Integer> {

}
