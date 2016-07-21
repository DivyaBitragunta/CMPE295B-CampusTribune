package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.EventUserDAO;

public interface IEventUserRepository extends MongoRepository<EventUserDAO, String> {

}
