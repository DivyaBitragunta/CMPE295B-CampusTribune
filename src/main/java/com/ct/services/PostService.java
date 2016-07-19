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
import com.ct.dao.PostUserDAO;
import com.ct.model.Post;
import com.ct.repositories.IPostRepository;
import com.ct.repositories.IPostUserRepository;

@Service
public class PostService {

	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IPostUserRepository postUserRepo;
	
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
	
	public boolean userActionExists(String userid) {
		if (postUserRepo.findByUser(userid)==null)
			return false;
		return true;
	}
	
	public Post votePost(int voteType,String user_id,Post post){
		PostDAO postDAO = new PostDAO();
		postDAO=postRepo.findById(post.getId());
		int newVoteScore= Vote.calculateVoteScore(voteType, postDAO.getVoteScore());
		postDAO.setVoteScore(newVoteScore);
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		if (postRepo.save(postDAO) != null) {
			post.setVoteScore(postDAO.getVoteScore());
			post.setLastEditedOn(postDAO.getLastEditedOn());
			PostUserDAO userActionsDAO=postUserRepo.findByUser(user_id);
			if(userActionsDAO!=null){
				updateUserVoteActions(voteType,post.getId(),userActionsDAO);
				postUserRepo.save(userActionsDAO);
			}else{
				PostUserDAO userActionsDAO1 = new PostUserDAO();
				userActionsDAO1.setUser(user_id);
				updateUserVoteActions(voteType,post.getId(),userActionsDAO1);
				postUserRepo.save(userActionsDAO1);
			}
			
		}
		return post;
	}
	
	public Post reportPost(String user_id,Post post){
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
				PostUserDAO userActionsDAO=postUserRepo.findByUser(user_id);
				if(userActionsDAO!=null){
					userActionsDAO.getReportedPosts().add(post.getId());
					postUserRepo.save(userActionsDAO);
				}else{
					PostUserDAO userActionsDAO1 = new PostUserDAO();
					int id = generateId();
					while (postRepo.exists(id))
						id = generateId();
					userActionsDAO1.setUser(user_id);
					userActionsDAO1.getReportedPosts().add(post.getId());
					postUserRepo.save(userActionsDAO1);
				}
				
			}
			return post;
		}
	}
	
	public void followPost(String user_id,Integer post_id){
		PostUserDAO userActionsDAO = postUserRepo.findByUser(user_id);
		if (userActionsDAO != null) {
			if(userActionsDAO.getFollowingPosts().contains(post_id))
				userActionsDAO.getFollowingPosts().remove(post_id);
			else
				userActionsDAO.getFollowingPosts().add(post_id);
			postUserRepo.save(userActionsDAO);
		} else {
			PostUserDAO userActionsDAO1 = new PostUserDAO();
			int id = generateId();
			while (postRepo.exists(id))
				id = generateId();
			userActionsDAO1.setUser(user_id);
			userActionsDAO1.getFollowingPosts().add(post_id);
			postUserRepo.save(userActionsDAO1);
		}
	}
	
	private void  updateUserVoteActions(int voteType,int postid,PostUserDAO userActionsDAO){
		if(voteType==1)
			userActionsDAO.getUpVotedPosts().add(postid);
		else if(voteType==2)
			userActionsDAO.getUpVotedPosts().remove(postid);
		else if(voteType==3)
			userActionsDAO.getDownVotedPosts().add(postid);
		else if(voteType==4)
			userActionsDAO.getDownVotedPosts().remove(postid);
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
