package com.ct.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "postcomments")
public class PostCommentDAO {

	@Id
	private int id;
	private int postId;
	private String commentContent;
	private int userId;
	private int reportScore;
	private String createdOn;
	private String lastEditedOn;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the postId
	 */
	public int getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}

	/**
	 * @return the commentContent
	 */
	public String getCommentContent() {
		return commentContent;
	}

	/**
	 * @param commentContent
	 *            the commentContent to set
	 */
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	/**
	 * @return the reportScore
	 */
	public int getReportScore() {
		return reportScore;
	}

	/**
	 * @param reportScore the reportScore to set
	 */
	public void setReportScore(int reportScore) {
		this.reportScore = reportScore;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastEditedOn
	 */
	public String getLastEditedOn() {
		return lastEditedOn;
	}

	/**
	 * @param lastEditedOn
	 *            the lastEditedOn to set
	 */
	public void setLastEditedOn(String lastEditedOn) {
		this.lastEditedOn = lastEditedOn;
	}

}
