package com.ct.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ct.dao.EventCommentsDAO;
import com.ct.dao.EventDAO;
import com.ct.dao.EventUserDAO;
import com.ct.exceptions.AuthenticationException;
import com.ct.exceptions.InvalidRequestTypeException;
import com.ct.exceptions.RequestFailedTypeException;
import com.ct.model.Event;
import com.ct.model.EventComment;
import com.ct.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, BindingResult bindingResult)
			throws InvalidRequestTypeException{
		
		if(bindingResult.hasErrors())
			throw new InvalidRequestTypeException("Create Event: Invalid Request", bindingResult);
		
		Event createdEvent = eventService.createEvent(event);
		if(createdEvent !=null){
			System.out.println("EventDAO created successfully ");
			return new ResponseEntity<Event>(createdEvent, HttpStatus.CREATED);
		}
		else
			return new ResponseEntity<Event>(event, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/{university_name}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<Event>> viewEvents(@PathVariable("university_name") String university){
		
		ArrayList<Event> events = eventService.viewEvents(university);
		if(events!=null){
				System.out.println("GET EVENT LISTS REST RESPONSE ----------> Returning "+ events.size()+" events");
				return new ResponseEntity<ArrayList<Event>>(events, HttpStatus.OK);
		}
		else
			return new ResponseEntity<ArrayList<Event>>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(value = "/{event_id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<HttpStatus> updateEvent(@PathVariable("event_id") UUID eventId,
											@RequestBody Event event){
		
		if(event!=null){
			if(event.getId()==null)
				event.setId(eventId);
		}
		try{
			eventService.updateEvent(event);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	

	@RequestMapping(value = "/{event_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("event_id") UUID eventId){
		
		try{
			eventService.deleteEvent(eventId);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}


}
