package com.ct.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ct.dao.EventCommentsDAO;
import com.ct.dao.EventDAO;
import com.ct.dao.EventUserDAO;
import com.ct.model.Event;
import com.ct.model.EventComment;
import com.ct.repositories.IEventCommentsRepository;
import com.ct.repositories.IEventRepository;
import com.ct.repositories.IEventUserRepository;

@Service
public class EventService {
	@Autowired
	private IEventRepository eventRepository;
	
	@Autowired
	private IEventCommentsRepository commentRepository;
	
	@Autowired
	private IEventUserRepository eventUserRepository;
	
	public Event createEvent(Event event){
		EventDAO eventDAO = initializeDAOUsingEntity(event);
		eventRepository.save(eventDAO);
		event.setId(eventDAO.getId());
		event = this.updateCreatedEvent(event);
		return event;
	}
	
	private EventDAO initializeDAOUsingEntity(Event event){
		EventDAO eventDAO = new EventDAO();
		eventDAO.setId(UUID.randomUUID());
		eventDAO.setTitle(event.getTitle());
		eventDAO.setCategory(event.getCategory());
		eventDAO.setDescription(event.getDescription());
		eventDAO.setUrl(event.getUrl());
		eventDAO.setStartDate(event.getStartDate());
		eventDAO.setEndDate(event.getEndDate());
		eventDAO.setLatitude(event.getLatitude());
		eventDAO.setLongitude(event.getLongitude());
		eventDAO.setAddress(event.getAddress());
		eventDAO.setEventImageS3URL(event.getEventImageS3URL());
		eventDAO.setUniversity(event.getUniversity());
		eventDAO.setCreatedBy(event.getCreatedBy());
		eventDAO.setCreatedOn(event.getCreatedOn());
		DateTime dt = new DateTime(DateTimeZone.UTC);		
		eventDAO.setLastUpdatedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		return eventDAO;
	}
	
	private Event updateCreatedEvent(Event event){
		if(event.isUpvoted())
			upvoteEvent(event);
		
		if(event.isDownvoted())
			downvoteEvent(event);
		
		if(event.isFollow())
			followEvent(event);
		
		if(event.isGoing())
			markGoing(event);
		
		if(event.isNotGoing())
			markNotGoing(event);
		
		if(event.getListOfComments()!=null && event.getListOfComments().size()>0){
			ArrayList<EventComment> listOfComments = event.getListOfComments();
			ArrayList<EventComment> returnListOfComments = new ArrayList<>();
			for(EventComment eachComment: listOfComments){
				eachComment.setEventId(event.getId());
				EventCommentsDAO eventCommentDAO = createComment(eachComment);
				eachComment.setId(eventCommentDAO.getId());
				returnListOfComments.add(eachComment);
			}
			event.setListOfComments(returnListOfComments);
		}
		return event;
	}
	
	public ArrayList<Event> viewEvents(String university){
		List<EventDAO> events = new ArrayList<>();
		events = eventRepository.findFirst10ByUniversityOrderByLastUpdatedOnDesc(university);
		if(events.size()>0){
			ArrayList<Event> listOfEvents = new ArrayList<>(events.size());
			for(EventDAO each: events){
				Event eachEvent = new Event(each.getId(), each.getTitle(), each.getCategory(),
						each.getDescription(), each.getUrl(), each.getStartDate(), each.getEndDate(),
						each.getLatitude(), each.getLongitude(), each.getAddress(), each.getEventImageS3URL(), each.getUniversity(),
						each.getUpVoteCount(), each.getDownVoteCount(), each.getGoingCount(), each.getNotGoingCount(),
						each.getCreatedBy(), each.getCreatedOn());
				eachEvent.setListOfComments(getAllComments(eachEvent));
				listOfEvents.add(eachEvent);
			}
			return listOfEvents;
		}
		else
			return null;
	}
	
	public void updateEvent(Event event){
		if(event!=null && event.getId()!=null){
			
			if(event.isUpdateEvent()){
				updateDAOUsingEntity(event);
				//eventRepository.save(newEventDAO);
			}
				
			if(event.isUpdateComments()){
				ArrayList<EventComment> listOfCommentsToUpdate = event.getListOfComments();
				if(listOfCommentsToUpdate!=null && listOfCommentsToUpdate.size()>0){
					for(EventComment eachComm: listOfCommentsToUpdate){
						
						if(eachComm.getId()==null){
							eachComm.setEventId(event.getId());
							createComment(eachComm);
						}
						else
							updateComment(eachComm);				
					}
				}
			}
			
			if(event.isDeleteComments()){
				ArrayList<EventComment> listOfCommentsToDelete = event.getListOfDeletedComments();
				if(listOfCommentsToDelete!=null && listOfCommentsToDelete.size()>0){
					for(EventComment each: listOfCommentsToDelete){
						deleteComment(each);
					}
				}
			}
			
		} 
	}
	
	private void updateDAOUsingEntity(Event event){
		
		if(event.getUpdatedBy().equals(event.getCreatedBy())){
			EventDAO eventDAO = new EventDAO();
			eventDAO.setId(event.getId());
			eventDAO.setTitle(event.getTitle());
			eventDAO.setCategory(event.getCategory());
			eventDAO.setDescription(event.getDescription());
			eventDAO.setUrl(event.getUrl());
			eventDAO.setStartDate(event.getStartDate());
			eventDAO.setEndDate(event.getEndDate());
			eventDAO.setLatitude(event.getLatitude());
			eventDAO.setLongitude(event.getLongitude());
			eventDAO.setAddress(event.getAddress());
			eventDAO.setEventImageS3URL(event.getEventImageS3URL());
			eventDAO.setCreatedBy(event.getCreatedBy());
			eventDAO.setCreatedOn(event.getCreatedOn());
			DateTime dt = new DateTime(DateTimeZone.UTC);		
			eventDAO.setLastUpdatedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			eventRepository.save(eventDAO);
		}
		
		if(event.isUpvoted())
			upvoteEvent(event);
		
		if(event.isDownvoted())
			downvoteEvent(event);
		
		if(event.isFollow())
			followEvent(event);
		
		if(event.isGoing())
			markGoing(event);
		
		if(event.isNotGoing())
			markNotGoing(event);
		
		if(event.isReported())
			reportEvent(event);
			
	}
	
	public void deleteEvent(UUID eventId){
		EventDAO eventDAO = eventRepository.findOne(eventId);
		if(eventDAO!=null){
			commentRepository.deleteByEventId(eventId);	
			eventRepository.delete(eventDAO);
		}
	}
	
	public EventCommentsDAO createComment(EventComment comment){
		EventCommentsDAO commentDAO = new EventCommentsDAO();
		if(comment!=null){
			commentDAO.setId(UUID.randomUUID());
			commentDAO.setEventId(comment.getEventId());
			commentDAO.setCreatedBy(comment.getCreatedBy());
			commentDAO.setCreatedOn(comment.getCreatedOn());
			DateTime today = new DateTime(DateTimeZone.UTC);
			commentDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			commentDAO.setComment(comment.getComment());
		}
		commentRepository.save(commentDAO);
		return commentDAO;
	}
	
	public ArrayList<EventComment> getAllComments(Event event){
		List<EventCommentsDAO> listOfComments = new ArrayList<>();
		if(event!=null){
			listOfComments = commentRepository.findByEventId(event.getId());
			if(listOfComments.size()>0){
				ArrayList<EventComment> listOfExtractedComments = new ArrayList<>();
				for(EventCommentsDAO each: listOfComments){
					EventComment comm = new EventComment(each.getId(), each.getEventId(), each.getCreatedBy(),
							each.getCreatedOn(), each.getComment(), null);
					listOfExtractedComments.add(comm);
				}
				return listOfExtractedComments;
			}
		}
		return null;
	}
	
	public EventCommentsDAO updateComment(EventComment comment){
		EventCommentsDAO commentDAO = new EventCommentsDAO();
		if(comment!=null){
			if(comment.getId()==null || comment.getCreatedBy()==null)
				return null;
			commentDAO = commentRepository.findOne(comment.getId());
			if(commentDAO==null)
				return null;
			DateTime today = new DateTime(DateTimeZone.UTC);
			commentDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			commentDAO.setComment(comment.getComment());
			if(comment.getReportedBy()!=null && !comment.getReportedBy().isEmpty()){
				reportComment(comment);
			}
			commentRepository.save(commentDAO);
		}
		return commentDAO;
	}
	
	public EventCommentsDAO reportComment(EventComment comment){
		EventCommentsDAO commentDAO = new EventCommentsDAO();
		if(comment!=null){
			if(comment.getId()==null || comment.getReportedBy()==null)
				return null;
			commentDAO = commentRepository.findOne(comment.getId());
			if(commentDAO==null)
				return null;
			commentDAO.setReported(true);
			commentDAO.setReportCount(commentDAO.getReportCount()+1);
			DateTime today = new DateTime(DateTimeZone.UTC);
			commentDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			commentRepository.save(commentDAO);
			
			EventUserDAO eventUserDAO = eventUserRepository.findOne(comment.getReportedBy());
			List<UUID> listOfReportedComments = new ArrayList<>();
			if(eventUserDAO==null){
				eventUserDAO = new EventUserDAO();
				eventUserDAO.setUserName(comment.getReportedBy());
			}
			else{
				listOfReportedComments = eventUserDAO.getReportedComments();
			}
			listOfReportedComments.add(comment.getId());
			eventUserDAO.setReportedComments(listOfReportedComments);
			eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			eventUserRepository.save(eventUserDAO);
		}
		return commentDAO;
	}
	
	public EventCommentsDAO deleteComment(EventComment comment){
		EventCommentsDAO commentDAO = new EventCommentsDAO();
		if(comment!=null){
			if(comment.getId()==null || comment.getCreatedBy()==null)
				return null;
			commentDAO = commentRepository.findOne(comment.getId());
			if(commentDAO==null)
				return null;
			commentRepository.delete(commentDAO);
		}
		return commentDAO;
	}
	
	public EventDAO upvoteEvent(Event event){
		EventDAO eventDAO = new EventDAO();
		if(event!=null){
			if(event.getId()==null || event.getCreatedBy()==null)
				return null;
			eventDAO = eventRepository.findOne(event.getId());
			if(eventDAO==null)
				return null;
			eventDAO.setUpVoteCount(eventDAO.getUpVoteCount()+1);
			eventRepository.save(eventDAO);
			
			EventUserDAO eventUserDAO;
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
			else
				eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
			
			List<UUID> listOfUpvotedEvents = new ArrayList<>();
			if(eventUserDAO!=null)
				listOfUpvotedEvents = eventUserDAO.getUpVotedEvents();
			else{
				eventUserDAO = new EventUserDAO();
				if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
					eventUserDAO.setUserName(event.getUpdatedBy());
				else
					eventUserDAO.setUserName(event.getCreatedBy());
			}
			listOfUpvotedEvents.add(event.getId());
			eventUserDAO.setUpVotedEvents(listOfUpvotedEvents);
			DateTime today = new DateTime(DateTimeZone.UTC);
			eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			eventUserRepository.save(eventUserDAO);
		}
		return eventDAO;
	}
	
	public EventDAO downvoteEvent(Event event){
		EventDAO eventDAO = new EventDAO();
		if(event!=null){
			if(event.getId()==null || event.getCreatedBy()==null)
				return null;
			eventDAO = eventRepository.findOne(event.getId());
			if(eventDAO==null)
				return null;
			eventDAO.setDownVoteCount(eventDAO.getDownVoteCount()+1);
			eventRepository.save(eventDAO);
			
			EventUserDAO eventUserDAO;
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
			else
				eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
			
			List<UUID> listOfDownvotedEvents = new ArrayList<>();
			if(eventUserDAO!=null)
				listOfDownvotedEvents = eventUserDAO.getDownVotedEvents();
			else{
				eventUserDAO = new EventUserDAO();
				if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
					eventUserDAO.setUserName(event.getUpdatedBy());
				else
					eventUserDAO.setUserName(event.getCreatedBy());
			}
			listOfDownvotedEvents.add(event.getId());
			eventUserDAO.setDownVotedEvents(listOfDownvotedEvents);
			DateTime today = new DateTime(DateTimeZone.UTC);
			eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			eventUserRepository.save(eventUserDAO);
		}
		return eventDAO;
	}
	
	public EventUserDAO followEvent(Event event){
		EventUserDAO eventUserDAO = new EventUserDAO();
		if(event.getCreatedBy()==null || event.getId()==null)
			return null;
		
		if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
			eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
		else
			eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
		
		List<UUID> listOfFollowingEvents = new ArrayList<>();
		if(eventUserDAO==null){
			eventUserDAO = new EventUserDAO();
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO.setUserName(event.getUpdatedBy());
			else
				eventUserDAO.setUserName(event.getCreatedBy());
		}
		else
			listOfFollowingEvents = eventUserDAO.getFollowingEvents();
		listOfFollowingEvents.add(event.getId());
		eventUserDAO.setFollowingEvents(listOfFollowingEvents);
		DateTime today = new DateTime(DateTimeZone.UTC);
		eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventUserRepository.save(eventUserDAO);
		return eventUserDAO;
	}
	
	public EventUserDAO unfollowEvent(Event event){
		EventUserDAO eventUserDAO = new EventUserDAO();
		if(event.getCreatedBy()==null || event.getId()==null)
			return null;
		eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
		List<UUID> listOfFollowingEvents = new ArrayList<>();
		if(eventUserDAO==null){
			eventUserDAO = new EventUserDAO();
			eventUserDAO.setUserName(event.getCreatedBy());
		}
		else
			listOfFollowingEvents = eventUserDAO.getFollowingEvents();
		listOfFollowingEvents.remove(event.getId());
		eventUserDAO.setFollowingEvents(listOfFollowingEvents);
		DateTime today = new DateTime(DateTimeZone.UTC);
		eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventUserRepository.save(eventUserDAO);
		return eventUserDAO;
	}
	
	public EventDAO reportEvent(Event event){
		EventDAO eventDAO = new EventDAO();
		if(event.getId()==null || event.getCreatedBy()==null)
			return null;
		eventDAO = eventRepository.findOne(event.getId());
		if(eventDAO==null)
			return null;
		eventDAO.setReported(true);
		eventDAO.setReportCount(eventDAO.getReportCount()+1);
		DateTime today = new DateTime(DateTimeZone.UTC);
		eventDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventRepository.save(eventDAO);
		
		EventUserDAO eventUserDAO;
		if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
			eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
		else
			eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
		
		List<UUID> listOfReportedEvents = new ArrayList<>();
		if(eventUserDAO==null){
			eventUserDAO = new EventUserDAO();
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO.setUserName(event.getUpdatedBy());
			else
				eventUserDAO.setUserName(event.getCreatedBy());
		}
		else
			listOfReportedEvents = eventUserDAO.getReportedEvents();
		listOfReportedEvents.add(event.getId());
		eventUserDAO.setReportedEvents(listOfReportedEvents);
		eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventUserRepository.save(eventUserDAO);
		
		return eventDAO;
	}
	
	public EventDAO markGoing(Event event){
		EventDAO eventDAO = new EventDAO();
		if(event.getId()==null || event.getCreatedBy()==null)
			return null;
		eventDAO = eventRepository.findOne(event.getId());
		if(eventDAO==null)
			return null;
		eventDAO.setGoingCount(eventDAO.getGoingCount()+1);
		DateTime today = new DateTime(DateTimeZone.UTC);
		eventDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventRepository.save(eventDAO);
		
		EventUserDAO eventUserDAO;
		if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
			eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
		else
			eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
		
		List<UUID> listOfGoingEvents = new ArrayList<>();
		if(eventUserDAO==null){
			eventUserDAO = new EventUserDAO();
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO.setUserName(event.getUpdatedBy());
			else
				eventUserDAO.setUserName(event.getCreatedBy());
		}
		else
			listOfGoingEvents = eventUserDAO.getGoingEvents();
		listOfGoingEvents.add(event.getId());
		eventUserDAO.setGoingEvents(listOfGoingEvents);
		eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventUserRepository.save(eventUserDAO);
		
		return eventDAO;
	}
	
	public EventDAO markNotGoing(Event event){
		EventDAO eventDAO = new EventDAO();
		if(event.getId()==null || event.getCreatedBy()==null)
			return null;
		eventDAO = eventRepository.findOne(event.getId());
		if(eventDAO==null)
			return null;
		eventDAO.setNotGoingCount(eventDAO.getNotGoingCount()+1);
		DateTime today = new DateTime(DateTimeZone.UTC);
		eventDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventRepository.save(eventDAO);
		
		EventUserDAO eventUserDAO;
		if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
			eventUserDAO = eventUserRepository.findOne(event.getUpdatedBy());
		else
			eventUserDAO = eventUserRepository.findOne(event.getCreatedBy());
		
		List<UUID> listOfNotGoingEvents = new ArrayList<>();
		if(eventUserDAO==null){
			eventUserDAO = new EventUserDAO();
			if(event.getUpdatedBy()!=null && !event.getUpdatedBy().isEmpty())
				eventUserDAO.setUserName(event.getUpdatedBy());
			else
				eventUserDAO.setUserName(event.getCreatedBy());
		}
		else
			listOfNotGoingEvents = eventUserDAO.getNotgoingEvents();
		listOfNotGoingEvents.add(event.getId());
		eventUserDAO.setNotgoingEvents(listOfNotGoingEvents);
		eventUserDAO.setLastUpdatedOn(today.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		eventUserRepository.save(eventUserDAO);
		
		return eventDAO;
	}
	
	public EventUserDAO getEventUserDetails(String userName){
		return eventUserRepository.findOne(userName);
	}
	

}
