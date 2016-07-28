package com.ct.dao;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "users")
public class UserDAO {
	@Null
	@Id
	private String id;
	
	@NotNull
	@JsonProperty
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	//@Indexed(unique=true)
	private String email;
	
	@NotNull
	private String password;
	
	@Null
	private String created_at;
	
	private String university;
	private boolean sendNotifications;
	private boolean sendRecommendations;
	
	@JsonProperty
	private ArrayList<String> subscriptionList;
	
	
	
	public ArrayList<String> getSubscriptionList() {
		return subscriptionList;
	}
	@JsonProperty("subscriptionList")
	public void setSubscriptionList(ArrayList<String> subscriptionList) {
		this.subscriptionList = subscriptionList;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public boolean isSendNotifications() {
		return sendNotifications;
	}
	public void setSendNotifications(boolean sendNotifications) {
		this.sendNotifications = sendNotifications;
	}
	public boolean isSendRecommendations() {
		return sendRecommendations;
	}
	public void setSendRecommendations(boolean sendRecommendations) {
		this.sendRecommendations = sendRecommendations;
	}
	
	
	
	
	
	
}
