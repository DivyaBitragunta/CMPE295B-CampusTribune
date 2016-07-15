/**
 * 
 */
package com.ct.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author snshr
 *
 */
@Document(collection = "useractionsforpost")
public class UserActionsForPostDAO {

	@Id
	private int id;
	private int postid;
	private int userid;
	private boolean isFollowedBy;
	private boolean isUpvotedBy;
	private boolean isDownvotedBy;
	private boolean isReportedBy;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the postid
	 */
	public int getPostid() {
		return postid;
	}
	/**
	 * @param postid the postid to set
	 */
	public void setPostid(int postid) {
		this.postid = postid;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the isFollowedBy
	 */
	public boolean isFollowedBy() {
		return isFollowedBy;
	}
	/**
	 * @param isFollowedBy the isFollowedBy to set
	 */
	public void setFollowedBy(boolean isFollowedBy) {
		this.isFollowedBy = isFollowedBy;
	}
	/**
	 * @return the isUpvotedBy
	 */
	public boolean isUpvotedBy() {
		return isUpvotedBy;
	}
	/**
	 * @param isUpvotedBy the isUpvotedBy to set
	 */
	public void setUpvotedBy(boolean isUpvotedBy) {
		this.isUpvotedBy = isUpvotedBy;
	}
	/**
	 * @return the isDownvotedBy
	 */
	public boolean isDownvotedBy() {
		return isDownvotedBy;
	}
	/**
	 * @param isDownvotedBy the isDownvotedBy to set
	 */
	public void setDownvotedBy(boolean isDownvotedBy) {
		this.isDownvotedBy = isDownvotedBy;
	}
	/**
	 * @return the isReportedBy
	 */
	public boolean isReportedBy() {
		return isReportedBy;
	}
	/**
	 * @param isReportedBy the isReportedBy to set
	 */
	public void setReportedBy(boolean isReportedBy) {
		this.isReportedBy = isReportedBy;
	}
	
	
	
}
