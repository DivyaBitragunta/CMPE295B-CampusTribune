package com.ct.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ct.dao.EventUserDAO;
import com.ct.model.Event;
import com.ct.services.EventService;

@RestController
@RequestMapping("/eventusers")
public class EventUserController {

	@Autowired
	EventService eventService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<EventUserDAO> getEventUserDetails(@PathVariable("username") String userName){
		
		EventUserDAO eventUserDAO = eventService.getEventUserDetails(userName);
		if(eventUserDAO!=null){
				return new ResponseEntity<EventUserDAO>(eventUserDAO, HttpStatus.OK);
		}
		else
			return new ResponseEntity<EventUserDAO>(new EventUserDAO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
