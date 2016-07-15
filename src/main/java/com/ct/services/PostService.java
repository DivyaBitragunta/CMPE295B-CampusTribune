package com.ct.services;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.algorithms.Report;
import com.ct.algorithms.Vote;
import com.ct.dao.PostDAO;
import com.ct.dao.UserActionsForPostDAO;
import com.ct.model.Post;
import com.ct.repositories.IPostRepository;
import com.ct.repositories.IUserActionsForPostRepository;

@Service
public class PostService {

	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IUserActionsForPostRepository userActionsRepo;
	
	public PostService() {
		super();
	}

	public Post createPost(Post post) {
		PostDAO postDAO = new PostDAO();
		int id = generateId();
		while (postRepo.exists(id))
			id = generateId();
		postDAO=setPostDAOObj(post, postDAO);
		postDAO.setId(id);
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postDAO.setCreatedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		postDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		if (postRepo.save(postDAO) != null) {
			post.setId(postDAO.getId());
			post.setCreatedOn(postDAO.getCreatedOn());
			post.setLastEditedOn(postDAO.getLastEditedOn());
		}
		return post;
	}

	public Post getPost(int id) {
		Post post = new Post();
		PostDAO postDAO = new PostDAO();
		postDAO = postRepo.findOne(id);
		post = setPostObj(post, postDAO);
		return post;

	}

	public Post updatePost(Post post) {
		PostDAO postDAO = new PostDAO();
		postDAO = postRepo.findById(post.getId());
		postDAO.setContent(post.getContent());
		postDAO.setHeadline(post.getHeadline());
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		if(postRepo.save(postDAO)!=null){
			post.setLastEditedOn(postDAO.getLastEditedOn());
		}
		return post;
	}

	public void deletePost(int id) {
		PostDAO postDAO = new PostDAO();
		postDAO = postRepo.findById(id);
		postRepo.delete(postDAO);

	}

	public ArrayList<Post> getPostsForCategory(String category) {
		ArrayList<Post> posts = new ArrayList<Post>();
		ArrayList<PostDAO> postDAOs = new ArrayList<PostDAO>();
		postDAOs = postRepo.findTop10ByCategoryOrderByLastEditedOnDesc(category);
		for (PostDAO postDAO : postDAOs) {
			Post post = new Post();
			post = setPostObj(post, postDAO);
			posts.add(post);
		}
		return posts;
	}

	public boolean postExists(int id) {
		if (!(postRepo.exists(id)))
			return false;
		return true;
	}
	
	public boolean userActionExists(int userid,int postid) {
		if (userActionsRepo.findByUseridAndPostid(userid, postid)==null)
			return false;
		return true;
	}
	
	public Post votePost(int voteType,int user_id,Post post){
		PostDAO postDAO = new PostDAO();
		postDAO=postRepo.findById(post.getId());
		int newVoteScore= Vote.calculateVoteScore(voteType, postDAO.getVoteScore());
		postDAO.setVoteScore(newVoteScore);
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		if (postRepo.save(postDAO) != null) {
			post.setVoteScore(postDAO.getVoteScore());
			post.setLastEditedOn(postDAO.getLastEditedOn());
			UserActionsForPostDAO userActionsDAO=userActionsRepo.findByUseridAndPostid(user_id, post.getId());
			if(userActionsDAO!=null){
				updateUserVoteActions(voteType,userActionsDAO);
				userActionsRepo.save(userActionsDAO);
			}else{
				UserActionsForPostDAO userActionsDAO1 = new UserActionsForPostDAO();
				int id = generateId();
				while (postRepo.exists(id))
					id = generateId();
				userActionsDAO1.setId(id);
				userActionsDAO1.setUserid(user_id);
				userActionsDAO1.setPostid(post.getId());
				updateUserVoteActions(voteType,userActionsDAO1);
				userActionsRepo.save(userActionsDAO1);
			}
			
		}
		return post;
	}
	
	public Post reportPost(int user_id,Post post){
		PostDAO postDAO = new PostDAO();
		postDAO=postRepo.findById(post.getId());
		int newReportScore=Report.updateReportScore(postDAO.getReportScore());
		if(Report.removeContent(newReportScore)){
			postRepo.delete(postDAO.getId());
			return null;
		}else{
			DateTime dt = new DateTime(DateTimeZone.UTC);
			postDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			postDAO.setReportScore(newReportScore);
			if(postRepo.save(postDAO)!=null){
				post.setReportScore(postDAO.getReportScore());
				post.setLastEditedOn(postDAO.getLastEditedOn());
				UserActionsForPostDAO userActionsDAO=userActionsRepo.findByUseridAndPostid(user_id, post.getId());
				if(userActionsDAO!=null){
					userActionsDAO.setReportedBy(true);
					userActionsRepo.save(userActionsDAO);
				}else{
					UserActionsForPostDAO userActionsDAO1 = new UserActionsForPostDAO();
					int id = generateId();
					while (postRepo.exists(id))
						id = generateId();
					userActionsDAO1.setId(id);
					userActionsDAO1.setUserid(user_id);
					userActionsDAO1.setPostid(post.getId());
					userActionsDAO1.setReportedBy(true);
					userActionsRepo.save(userActionsDAO1);
				}
				
			}
			return post;
		}
	}
	
	public void followPost(int user_id,int post_id){
		UserActionsForPostDAO userActionsDAO = userActionsRepo.findByUseridAndPostid(user_id, post_id);
		if (userActionsDAO != null) {
			if(userActionsDAO.isFollowedBy())
				userActionsDAO.setFollowedBy(false);
			else
				userActionsDAO.setFollowedBy(true);
			userActionsRepo.save(userActionsDAO);
		} else {
			UserActionsForPostDAO userActionsDAO1 = new UserActionsForPostDAO();
			int id = generateId();
			while (postRepo.exists(id))
				id = generateId();
			userActionsDAO1.setId(id);
			userActionsDAO1.setUserid(user_id);
			userActionsDAO1.setPostid(post_id);
			userActionsDAO1.setFollowedBy(true);
			userActionsRepo.save(userActionsDAO1);
		}
	}
	
	private void  updateUserVoteActions(int voteType,UserActionsForPostDAO userActionsDAO){
		if(voteType==1)
			userActionsDAO.setUpvotedBy(true);
		else if(voteType==2)
			userActionsDAO.setUpvotedBy(false);
		else if(voteType==3)
			userActionsDAO.setDownvotedBy(true);
		else if(voteType==4)
			userActionsDAO.setDownvotedBy(false);
	}

	private Integer generateId() {
		Random r = new Random();
		return r.nextInt(9000) + 1000;
	}
	
	private PostDAO setPostDAOObj(Post post, PostDAO postDAO){
		postDAO.setId(post.getId());
		postDAO.setHeadline(post.getHeadline());
		postDAO.setContent(post.getContent());
		postDAO.setUserId(post.getUserId());
		postDAO.setVoteScore(post.getVoteScore());
		postDAO.setIsAlert(post.getIsAlert());
		postDAO.setCategory(post.getCategory());
		postDAO.setWebLink(post.getWebLink());
		postDAO.setImgURL(post.getImgURL());
		postDAO.setReportScore(post.getReportScore());
		postDAO.setCreatedOn(post.getCreatedOn());
		postDAO.setLastEditedOn(post.getLastEditedOn());
		return postDAO;
	}

	private Post setPostObj(Post post, PostDAO postDAO) {
		post.setId(postDAO.getId());
		post.setHeadline(postDAO.getHeadline());
		post.setContent(postDAO.getContent());
		post.setUserId(postDAO.getUserId());
		post.setVoteScore(postDAO.getVoteScore());
		post.setIsAlert(postDAO.getIsAlert());
		post.setCategory(postDAO.getCategory());
		post.setWebLink(postDAO.getWebLink());
		post.setImgURL(postDAO.getImgURL());
		post.setReportScore(postDAO.getReportScore());
		post.setCreatedOn(postDAO.getCreatedOn());
		post.setLastEditedOn(postDAO.getLastEditedOn());
		return post;
	}
	
	

}
