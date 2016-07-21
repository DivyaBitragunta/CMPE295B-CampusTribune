package com.ct.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.EventCommentsDAO;


public interface IEventCommentsRepository extends MongoRepository<EventCommentsDAO, UUID> {
	public List<EventCommentsDAO> findByEventId(UUID eventId);
	public void deleteByEventId(UUID eventId);
}
