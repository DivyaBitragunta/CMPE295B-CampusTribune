package com.ct.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

public class Event {
	
	private UUID id;
	@NotNull
	private String title;
	@NotNull
	private String category;
	@NotNull
	private String description;
	private String url;
	@NotNull
	private String startDate;
	@NotNull
	private String endDate;
	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;
	@NotNull
	private String address;
	private String eventImageS3URL;
	private String university;
	
	private boolean upvoted;
    private boolean downvoted;
    private boolean follow;
    private boolean going;
    private boolean notGoing;
    private boolean reported;
    
    private Integer upVoteCount = new Integer(0);
	private Integer downVoteCount = new Integer(0);
	private Integer goingCount = new Integer(0);
	private Integer notGoingCount = new Integer(0);
	private Integer followCount = new Integer(0);
	
	private boolean updateEvent;
	private boolean updateComments;
	private boolean deleteComments;
	
	@NotNull
	private String createdBy;
	private String updatedBy;
	@NotNull
	private String createdOn;
	
	private ArrayList<EventComment> listOfComments = new ArrayList<>();
	private ArrayList<EventComment> listOfDeletedComments = new ArrayList<>();
	
	public Event(){}
	
	public Event(UUID id, String title, String category, String description, String url, String startDate,
			String endDate, Double latitude, Double longitude, String address, String eventImageS3URL, String university,
			Integer upVoteCount, Integer downVoteCount, Integer goingCount, Integer notGoingCount, Integer followCount,
			String createdBy, String createdOn) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.description = description;
		this.url = url;
		this.startDate = startDate;
		this.endDate = endDate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.eventImageS3URL = eventImageS3URL;
		this.university = university;
		this.upVoteCount = upVoteCount;
		this.downVoteCount = downVoteCount;
		this.goingCount = goingCount;
		this.notGoingCount = notGoingCount;
		this.followCount = followCount;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEventImageS3URL() {
		return eventImageS3URL;
	}

	public void setEventImageS3URL(String eventImageS3URL) {
		this.eventImageS3URL = eventImageS3URL;
	}
	
	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public boolean isUpvoted() {
		return upvoted;
	}

	public void setUpvoted(boolean upvoted) {
		this.upvoted = upvoted;
	}

	public boolean isDownvoted() {
		return downvoted;
	}

	public void setDownvoted(boolean downvoted) {
		this.downvoted = downvoted;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public boolean isGoing() {
		return going;
	}

	public void setGoing(boolean going) {
		this.going = going;
	}

	public boolean isNotGoing() {
		return notGoing;
	}

	public void setNotGoing(boolean notGoing) {
		this.notGoing = notGoing;
	}
	
	public Integer getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(Integer upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	public Integer getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(Integer downVoteCount) {
		this.downVoteCount = downVoteCount;
	}

	public Integer getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(Integer goingCount) {
		this.goingCount = goingCount;
	}

	public Integer getNotGoingCount() {
		return notGoingCount;
	}

	public void setNotGoingCount(Integer notGoingCount) {
		this.notGoingCount = notGoingCount;
	}
	
	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	public boolean isUpdateEvent() {
		return updateEvent;
	}

	public void setUpdateEvent(boolean updateEvent) {
		this.updateEvent = updateEvent;
	}

	public boolean isUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(boolean updateComments) {
		this.updateComments = updateComments;
	}
	
	public boolean isReported() {
		return reported;
	}

	public void setReported(boolean reported) {
		this.reported = reported;
	}

	public boolean isDeleteComments() {
		return deleteComments;
	}

	public void setDeleteComments(boolean deleteComments) {
		this.deleteComments = deleteComments;
	}

	public ArrayList<EventComment> getListOfComments() {
		return listOfComments;
	}

	public void setListOfComments(ArrayList<EventComment> listOfComments) {
		this.listOfComments = listOfComments;
	}

	public ArrayList<EventComment> getListOfDeletedComments() {
		return listOfDeletedComments;
	}

	public void setListOfDeletedComments(ArrayList<EventComment> listOfDeletedComments) {
		this.listOfDeletedComments = listOfDeletedComments;
	}
	
}
