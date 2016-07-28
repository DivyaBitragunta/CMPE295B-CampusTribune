package com.ct.model;

import com.ct.dao.EventDAO;
import com.ct.dao.PostDAO;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String token;
	private String university;
	private Boolean isNotifyFlag;
	private Boolean isRecommendFlag;
	public List<PostDAO> postList = new ArrayList<PostDAO>();
	public List<String> subscriptionList= new ArrayList<String>();
	private List<Event> eventList = new ArrayList<Event>();
	
	
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(ArrayList<Event> arrayList) {
		this.eventList = arrayList;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public Boolean getIsNotifyFlag() {
		return isNotifyFlag;
	}
	public void setIsNotifyFlag(Boolean isNotifyFlag) {
		this.isNotifyFlag = isNotifyFlag;
	}
	public Boolean getIsRecommendFlag() {
		return isRecommendFlag;
	}
	public void setIsRecommendFlag(Boolean isRecommendFlag) {
		this.isRecommendFlag = isRecommendFlag;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setPostList(ArrayList<PostDAO> findAll) {
		this.postList=findAll;
		
	}
	
	
	

}
