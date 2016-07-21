package com.ct.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;


public class EventComment {
	
	private UUID id;
	private UUID eventId;
	@NotNull
	private String createdBy;
	@NotNull
	private String createdOn;
	@NotNull
	private String comment;
	private String reportedBy;
	
	public EventComment(){}
	
	public EventComment(UUID id, UUID eventId, String createdBy, String createdOn, String comment, String reportedBy) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.comment = comment;
		this.reportedBy = reportedBy;
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEventId() {
		return eventId;
	}

	public void setEventId(UUID eventId) {
		this.eventId = eventId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

}
