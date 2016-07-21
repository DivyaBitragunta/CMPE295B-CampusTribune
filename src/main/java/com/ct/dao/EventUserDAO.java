package com.ct.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="eventuser")
public class EventUserDAO {
	
	@Id
	private String userName;
	private List<UUID> followingEvents = new ArrayList<>();
	private List<UUID> upVotedEvents = new ArrayList<>();
	private List<UUID> downVotedEvents = new ArrayList<>();
	private List<UUID> reportedEvents = new ArrayList<>();
	private List<UUID> reportedComments = new ArrayList<>();
	private List<UUID> goingEvents = new ArrayList<>();
	private List<UUID> notgoingEvents = new ArrayList<>();
	private String lastUpdatedOn;
	
	public EventUserDAO(){}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EventUserDAO:[username= "+userName+", no. of events following= "+followingEvents.size()+
				", no. of events upvoted= "+upVotedEvents.size()+", no. of events downvoted= "+downVotedEvents.size()+
				", no. of events reported= "+reportedEvents.size()+", no. of comments reported= "+reportedComments.size()+", no. of events going= "+
				goingEvents.size()+", No.of events not going= "+ notgoingEvents.size()+", last updated on= "+lastUpdatedOn+"]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UUID> getFollowingEvents() {
		return followingEvents;
	}

	public void setFollowingEvents(List<UUID> followingEvents) {
		this.followingEvents = followingEvents;
	}

	public List<UUID> getUpVotedEvents() {
		return upVotedEvents;
	}

	public void setUpVotedEvents(List<UUID> upVotedEvents) {
		this.upVotedEvents = upVotedEvents;
	}

	public List<UUID> getDownVotedEvents() {
		return downVotedEvents;
	}

	public void setDownVotedEvents(List<UUID> downVotedEvents) {
		this.downVotedEvents = downVotedEvents;
	}

	public List<UUID> getReportedEvents() {
		return reportedEvents;
	}

	public void setReportedEvents(List<UUID> reportedEvents) {
		this.reportedEvents = reportedEvents;
	}

	public List<UUID> getReportedComments() {
		return reportedComments;
	}

	public void setReportedComments(List<UUID> reportedComments) {
		this.reportedComments = reportedComments;
	}
	
	public List<UUID> getGoingEvents() {
		return goingEvents;
	}

	public void setGoingEvents(List<UUID> goingEvents) {
		this.goingEvents = goingEvents;
	}

	public List<UUID> getNotgoingEvents() {
		return notgoingEvents;
	}

	public void setNotgoingEvents(List<UUID> notgoingEvents) {
		this.notgoingEvents = notgoingEvents;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((downVotedEvents == null) ? 0 : downVotedEvents.hashCode());
		result = prime * result + ((followingEvents == null) ? 0 : followingEvents.hashCode());
		result = prime * result + ((goingEvents == null) ? 0 : goingEvents.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + ((notgoingEvents == null) ? 0 : notgoingEvents.hashCode());
		result = prime * result + ((reportedComments == null) ? 0 : reportedComments.hashCode());
		result = prime * result + ((reportedEvents == null) ? 0 : reportedEvents.hashCode());
		result = prime * result + ((upVotedEvents == null) ? 0 : upVotedEvents.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventUserDAO other = (EventUserDAO) obj;
		if (downVotedEvents == null) {
			if (other.downVotedEvents != null)
				return false;
		} else if (!downVotedEvents.equals(other.downVotedEvents))
			return false;
		if (followingEvents == null) {
			if (other.followingEvents != null)
				return false;
		} else if (!followingEvents.equals(other.followingEvents))
			return false;
		if (goingEvents == null) {
			if (other.goingEvents != null)
				return false;
		} else if (!goingEvents.equals(other.goingEvents))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (notgoingEvents == null) {
			if (other.notgoingEvents != null)
				return false;
		} else if (!notgoingEvents.equals(other.notgoingEvents))
			return false;
		if (reportedComments == null) {
			if (other.reportedComments != null)
				return false;
		} else if (!reportedComments.equals(other.reportedComments))
			return false;
		if (reportedEvents == null) {
			if (other.reportedEvents != null)
				return false;
		} else if (!reportedEvents.equals(other.reportedEvents))
			return false;
		if (upVotedEvents == null) {
			if (other.upVotedEvents != null)
				return false;
		} else if (!upVotedEvents.equals(other.upVotedEvents))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
