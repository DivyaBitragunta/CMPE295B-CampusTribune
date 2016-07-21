package com.ct.dao;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ct.model.Event;

@Document(collection="events")
public class EventDAO {

	@Id
	private UUID id;
	private String title;
	private String description;
	private String category;
	private String url;
	private String startDate;
	private String endDate;
	private Double latitude = new Double(0);
	private Double longitude = new Double(0);
	private String address;
	private String eventImageS3URL;
	
	private Integer upVoteCount = new Integer(0);
	private Integer downVoteCount = new Integer(0);
	private Integer goingCount = new Integer(0);
	private Integer notGoingCount = new Integer(0);
	private boolean isReported;
	private Integer reportCount = new Integer(0);
	
	private String createdBy;
	private String createdOn;
	private String lastUpdatedOn;
	
	public EventDAO(){}
	
	

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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
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



	public boolean isReported() {
		return isReported;
	}



	public void setReported(boolean isReported) {
		this.isReported = isReported;
	}



	public Integer getReportCount() {
		return reportCount;
	}



	public void setReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}



	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}



	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EventDAO [id= "+id+", title= "+title+", category= "+category+", description= "+ description+", url= "+url+", startDate= "+startDate+", endDate= "+endDate+
				", latitude= "+latitude.doubleValue()+", longitude= "+longitude.doubleValue()+", address= "+address+
				", image S3 URL= "+eventImageS3URL+", upvote count= "+upVoteCount.intValue()+", downVote count= "+downVoteCount.intValue()+
				", Going= "+ goingCount+", Not Going= "+notGoingCount+", isReported= "+isReported+", report count= "+reportCount.intValue()+
				", created by= "+createdBy+", created on= "+createdOn+", last updated on= "+lastUpdatedOn+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((downVoteCount == null) ? 0 : downVoteCount.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((eventImageS3URL == null) ? 0 : eventImageS3URL.hashCode());
		result = prime * result + ((goingCount == null) ? 0 : goingCount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isReported ? 1231 : 1237);
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((notGoingCount == null) ? 0 : notGoingCount.hashCode());
		result = prime * result + ((reportCount == null) ? 0 : reportCount.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((upVoteCount == null) ? 0 : upVoteCount.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		EventDAO other = (EventDAO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (downVoteCount == null) {
			if (other.downVoteCount != null)
				return false;
		} else if (!downVoteCount.equals(other.downVoteCount))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (eventImageS3URL == null) {
			if (other.eventImageS3URL != null)
				return false;
		} else if (!eventImageS3URL.equals(other.eventImageS3URL))
			return false;
		if (goingCount == null) {
			if (other.goingCount != null)
				return false;
		} else if (!goingCount.equals(other.goingCount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isReported != other.isReported)
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (notGoingCount == null) {
			if (other.notGoingCount != null)
				return false;
		} else if (!notGoingCount.equals(other.notGoingCount))
			return false;
		if (reportCount == null) {
			if (other.reportCount != null)
				return false;
		} else if (!reportCount.equals(other.reportCount))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (upVoteCount == null) {
			if (other.upVoteCount != null)
				return false;
		} else if (!upVoteCount.equals(other.upVoteCount))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
