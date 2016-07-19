package com.ct.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class PostDAO {

	@Id
	private int id;
	private String headline;
	private String content;
	private String userId;
	private int voteScore;
	private boolean isAlert;
	private String category;
	private String webLink;
	private String imgURL;
	private int reportScore;
	private String createdOn;
	private String lastEditedOn;

	/**
	 * @return the postId
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * @param headline
	 *            the headline to set
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the voteScore
	 */
	public int getVoteScore() {
		return voteScore;
	}

	/**
	 * @param voteScore
	 *            the voteScore to set
	 */
	public void setVoteScore(int voteScore) {
		this.voteScore = voteScore;
	}

	/**
	 * @return the isAlert
	 */
	public boolean getIsAlert() {
		return isAlert;
	}

	/**
	 * @param isAlert
	 *            the isAlert to set
	 */
	public void setIsAlert(boolean isAlert) {
		this.isAlert = isAlert;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the webLink
	 */
	public String getWebLink() {
		return webLink;
	}

	/**
	 * @param webLink
	 *            the webLink to set
	 */
	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	/**
	 * @return the imgURL
	 */
	public String getImgURL() {
		return imgURL;
	}

	/**
	 * @param imgURL
	 *            the imgURL to set
	 */
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
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
