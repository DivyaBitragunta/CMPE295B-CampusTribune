package com.ct.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.ct.dao.EventDAO;

@Service
public interface IEventRepository extends MongoRepository<EventDAO, UUID> {
	public List<EventDAO> findFirst10ByOrderByCreatedOnDesc();
}
