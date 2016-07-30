package com.ct.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.dao.EventDAO;
import com.ct.dao.PostDAO;
import com.ct.dao.UserDAO;
import com.ct.model.Event;
import com.ct.repositories.IEventCommentsRepository;
import com.ct.repositories.IEventRepository;
import com.ct.repositories.IPostCommentRepository;
import com.ct.repositories.IPostRepository;
import com.ct.repositories.IUserDetailsRepository;
@Service
public class FrontPageEventService {

	@Autowired
	private IEventRepository eventRepo;
	
	@Autowired
	private IUserDetailsRepository userRepo;
	
	@Autowired
	private IEventCommentsRepository eventCommentsRepo;
	
	
	public ArrayList<Event> getfrontPageEventData(String userId) {
		ArrayList<Event> eventlist = new ArrayList<Event>();
		UserDAO user = userRepo.findById(userId);
		ArrayList<String>  userSubscription = user.getSubscriptionList();
		for(String category : userSubscription) {
			List<EventDAO> top2Posts = findTopEventsForCategory(category,user.getUniversity());
		}
		return eventlist;
	}

	public List<EventDAO> findTopEventsForCategory(String category, String university) {
		
		HashMap<UUID, Integer> postsScoreMap = new HashMap<UUID, Integer>();
		//Accumulate most recent posts based on last edited on
		//Will change to Top100 once data set up is done
		List<EventDAO> listOfEvents =eventRepo.findTop10ByCategoryAndUniversityOrderByStartDateDesc(category,university);

		for(EventDAO event:listOfEvents){
			int voteScore = findVoteScore(event.getId());
			int commentScore = findCommentScore(event.getId());
			int followScore = event.getFollowCount();
			int age = findAgeOfEvent(event.getId());
			int numberOfPeopleGoing = event.getGoingCount();
			int cumulativeScore=age-voteScore+commentScore+followScore+numberOfPeopleGoing;
			postsScoreMap.put(event.getId(), cumulativeScore);
		}
		
		return listOfEvents;
	}

	private int findVoteScore(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int findAgeOfEvent(UUID id) {
		DateTime postCreatedOn = new DateTime((eventRepo.findOne(id)).getCreatedOn());
		DateTime currentDateTime = new DateTime(DateTimeZone.UTC);
		Hours hours = Hours.hoursBetween(postCreatedOn, currentDateTime);
		
		
		Period p = new Period(postCreatedOn, currentDateTime);
		int hoursPeriod = p.getHours();
		int minutes = p.getMinutes();
		System.out.println("No of HOURS"+hoursPeriod);
		System.out.println("No of MINUTES"+minutes);
		System.out.println("");
		return hours.getHours();
		
	}


	private int findCommentScore(UUID id) {
		return (eventCommentsRepo.findByEventId(id)).size();
	}


	
	
	
	//Get List of Categories the subscribed to
	//For each post in the subscription list.. get the  scores
	//Sort by vote score
	
	//no of posts per category = length of front page/subscriptioncategories
	//select these many number of posts from each category based on score and add to list.
}
