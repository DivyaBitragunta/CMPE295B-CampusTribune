package com.ct.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.ct.dao.EventDAO;
import com.ct.dao.PostDAO;
import com.ct.model.Event;

@Service
public interface IEventRepository extends MongoRepository<EventDAO, UUID> {
	public List<EventDAO> findFirst10ByOrderByCreatedOnDesc();
	public List<EventDAO> findFirst10ByUniversityOrderByLastUpdatedOnDesc(String university);
	public List<EventDAO> findTop10ByCategoryAndUniversityOrderByStartDateDesc(String category, String university);
	//public List<EventDAO> findFirst10ByUniversityAndReportCountValueLessThan10OrderByLastUpdatedOnDesc(String univeristy);
}
