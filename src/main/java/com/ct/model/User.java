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
	private List<PostDAO> postList = new ArrayList<PostDAO>();
	private List<String> subscriptionList= new ArrayList<String>();
	private List<Event> eventList = new ArrayList<Event>();
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
	public void setToken(String token) {
		this.token = token;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
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
	public List<PostDAO> getPostList() {
		return postList;
	}
	public void setPostList(List<PostDAO> postList) {
		this.postList = postList;
	}
	public List<String> getSubscriptionList() {
		return subscriptionList;
	}
	public void setSubscriptionList(List<String> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
	
	
	
	
	

}
