package com.ct.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ct.dao.PostDAO;
import com.ct.model.Event;
import com.ct.model.Post;
import com.ct.services.FrontPageEventService;
import com.ct.services.FrontPagePostService;
import com.ct.services.PostService;
import com.ct.services.UserService;

import flexjson.JSONSerializer;
@RestController
@RequestMapping("/front-page")
public class FrontPageController {
	
	@Autowired
	private FrontPagePostService frontPostService;
	
	@Autowired
	private FrontPageEventService frontEventService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/post/{userId}", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<ArrayList<PostDAO>> getFrontPagePostData(@PathVariable String userId) {
			System.out.println("Reached front post page controller!");
			return new ResponseEntity<ArrayList<PostDAO>>((ArrayList<PostDAO>) frontPostService.getfrontPagePostData(userId), HttpStatus.OK);		  
					  
			 
		  
	}
	
	@RequestMapping(value="/event/{userId}", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<ArrayList<Event>> getFrontPageEventData(@PathVariable String userId) {
			System.out.println("Reached front event page controller!");
			return new ResponseEntity<ArrayList<Event>>((ArrayList<Event>) frontEventService.getfrontPageEventData(userId), HttpStatus.OK);		  
					  
			 
		  
	}

}
