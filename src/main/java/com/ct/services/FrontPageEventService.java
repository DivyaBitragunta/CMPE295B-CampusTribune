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
			List<EventDAO> topEvents = findTopEventsForCategory(category,user.getUniversity());
			for(EventDAO eventDAO:topEvents){
				Event event=new Event();
				//convert event dao to event
				eventlist.add(event);
			}
		}
		return eventlist;
	}

	public List<EventDAO> findTopEventsForCategory(String category, String university) {
		
		List<EventDAO> listOfEvents =eventRepo.findTop10ByCategoryAndUniversityOrderByStartDateDesc(category,university);
		int[] scoreArray = new int[listOfEvents.size()];
		int listIndex = 0;
		for(EventDAO event:listOfEvents){
			int voteScore = findVoteScore(event.getId());
			int commentScore = findCommentScore(event.getId());
			int followScore = event.getFollowCount();
			int age = findAgeOfEvent(event.getId());
			int numberOfPeopleGoing = event.getGoingCount();
			int cumulativeScore=voteScore+commentScore+followScore+numberOfPeopleGoing-age;
			scoreArray[listIndex++] = cumulativeScore;
		}
		
		return  getTop4(listOfEvents, scoreArray);
	}

	private List<EventDAO> getTop4(List<EventDAO> listOfEvents, int[] scoreArray) {
		List<EventDAO> topEvents = new ArrayList<EventDAO>();
		if(listOfEvents.size()<4){
			return listOfEvents;
		}
		for (int i = 0; i < 4; i++) {
			int highScore = Integer.MIN_VALUE;
			int highScoreIndex = i;
			for (int j = i; j < scoreArray.length; j++) {
				if (scoreArray[j] > highScore) {
					highScore = scoreArray[j];
					highScoreIndex = j;
				}
			}
			int temp = scoreArray[i];
			scoreArray[i] = highScore;
			scoreArray[highScoreIndex] = temp;
			topEvents.add(listOfEvents.get(highScoreIndex));
			EventDAO tempEvent = listOfEvents.get(i);
			listOfEvents.set(i, listOfEvents.get(highScoreIndex));
			listOfEvents.set(highScoreIndex, tempEvent);

		}
		return topEvents;
	}

	private int findVoteScore(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int findAgeOfEvent(UUID id) {
		DateTime postCreatedOn = new DateTime((eventRepo.findOne(id)).getCreatedOn());
		DateTime currentDateTime = new DateTime(DateTimeZone.UTC);
		Hours hours = Hours.hoursBetween(postCreatedOn, currentDateTime);
		return hours.getHours();
		
	}


	private int findCommentScore(UUID id) {
		return (eventCommentsRepo.findByEventId(id)).size();
	}
}
