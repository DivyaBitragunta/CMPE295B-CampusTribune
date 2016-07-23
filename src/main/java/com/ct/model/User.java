package com.ct.model;

import com.ct.dao.PostDAO;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String token;
	private Boolean isNotifyFlag;
	private Boolean isRecommendFlag;
	public ArrayList<PostDAO> postList = new ArrayList();
	
	
	

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
