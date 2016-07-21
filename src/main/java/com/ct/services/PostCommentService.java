package com.ct.services;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.algorithms.Report;
import com.ct.dao.PostCommentDAO;
import com.ct.dao.PostUserDAO;
import com.ct.model.PostComment;
import com.ct.repositories.IPostCommentRepository;
import com.ct.repositories.IPostUserRepository;

@Service
public class PostCommentService {

	@Autowired
	private IPostCommentRepository commentRepo;
	
	@Autowired
	private IPostUserRepository postUserRepo;

	public PostCommentService() {
		super();
	}

	public PostComment createComment(PostComment postComment) {
		PostCommentDAO postCommentDAO = new PostCommentDAO();
		int id = generateId();
		while (commentRepo.exists(id))
			id = generateId();
		postCommentDAO=setCommentDAOObj(postComment, postCommentDAO);
		postCommentDAO.setId(id);
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postCommentDAO.setCreatedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		postCommentDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		if (commentRepo.save(postCommentDAO) != null) {
			postComment.setId(postCommentDAO.getId());
			postComment.setCreatedOn(postCommentDAO.getCreatedOn());
			postComment.setLastEditedOn(postCommentDAO.getLastEditedOn());
		}
		return postComment;
	}

	public PostComment getComment(int id, int postId) {
		PostComment postComment = new PostComment();
		PostCommentDAO postCommentDAO = new PostCommentDAO();
		postCommentDAO = commentRepo.findByIdAndPostId(id, postId);
		postComment = setCommentObj(postComment, postCommentDAO);
		return postComment;
	}

	public ArrayList<PostComment> getAllComments(int postId) {
		ArrayList<PostComment> postComments = new ArrayList<PostComment>();
		ArrayList<PostCommentDAO> postCommentDAOs = new ArrayList<PostCommentDAO>();
		postCommentDAOs = commentRepo.findByPostId(postId);
		for (PostCommentDAO postCommentDAO : postCommentDAOs) {
			PostComment postComment = new PostComment();
			postComment=setCommentObj(postComment, postCommentDAO);
			postComments.add(postComment);
		}

		return postComments;
	}

	public PostComment updateComment(PostComment postComment) {
		PostCommentDAO postCommentDAO = new PostCommentDAO();
		postCommentDAO = commentRepo.findByIdAndPostId(postComment.getId(), postComment.getPostId());
		postCommentDAO.setCommentContent(postComment.getCommentContent());
		DateTime dt = new DateTime(DateTimeZone.UTC);
		postCommentDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
		commentRepo.save(postCommentDAO);
		return postComment;

	}

	public void deleteComment(int id, int postId) {
		PostCommentDAO postCommentDAO = new PostCommentDAO();
		postCommentDAO = commentRepo.findByIdAndPostId(id, postId);
		commentRepo.delete(postCommentDAO);
	}
	
	public PostComment reportComment(PostComment comment,String userId){
		PostCommentDAO postCommentDAO = new PostCommentDAO();
		postCommentDAO=commentRepo.findByIdAndPostId(comment.getId(),comment.getPostId());
		int newReportScore=Report.updateReportScore(postCommentDAO.getReportScore());
		if(Report.removeContent(newReportScore)){
			commentRepo.delete(postCommentDAO.getId());
			return null;
		}else{
			DateTime dt = new DateTime(DateTimeZone.UTC);
			postCommentDAO.setLastEditedOn(dt.toString(ISODateTimeFormat.dateTime().withZoneUTC()));
			postCommentDAO.setReportScore(newReportScore);
			if(commentRepo.save(postCommentDAO)!=null){
				setCommentObj(comment, postCommentDAO);
				comment.setReportScore(postCommentDAO.getReportScore());
				comment.setLastEditedOn(postCommentDAO.getLastEditedOn());
				PostUserDAO userActionsDAO=postUserRepo.findByUser(userId);
				if(userActionsDAO!=null){
					userActionsDAO.getReportedComments().add(comment.getId());
					postUserRepo.save(userActionsDAO);
				}else{
					PostUserDAO userActionsDAO1 = new PostUserDAO();
					userActionsDAO1.setUser(userId);
					userActionsDAO1.getReportedPosts().add(comment.getId());
					postUserRepo.save(userActionsDAO1);
				}
			}
			return comment;
		}
	}

	public boolean commentExists(int id) {
		if (!(commentRepo.exists(id)))
			return false;
		return true;
	}
	
	public boolean userActionExists(String userid) {
		if (postUserRepo.findByUser(userid)==null)
			return false;
		return true;
	}

	private Integer generateId() {
		Random r = new Random();
		return r.nextInt(9000) + 1000;
	}
	
	private PostCommentDAO setCommentDAOObj(PostComment comment, PostCommentDAO commentDAO){
		commentDAO.setId(comment.getId());
		commentDAO.setPostId(comment.getPostId());
		commentDAO.setCommentContent(comment.getCommentContent());
		commentDAO.setUserId(comment.getUserId());
		commentDAO.setReportScore(comment.getReportScore());
		commentDAO.setCreatedOn(comment.getCreatedOn());
		commentDAO.setLastEditedOn(comment.getLastEditedOn());
		return commentDAO;
	}

	private PostComment setCommentObj(PostComment comment, PostCommentDAO commentDAO) {
		comment.setId(commentDAO.getId());
		comment.setPostId(commentDAO.getPostId());
		comment.setCommentContent(commentDAO.getCommentContent());
		comment.setUserId(commentDAO.getUserId());
		comment.setReportScore(commentDAO.getReportScore());
		comment.setCreatedOn(commentDAO.getCreatedOn());
		comment.setLastEditedOn(commentDAO.getLastEditedOn());
		return comment;
	}

}
