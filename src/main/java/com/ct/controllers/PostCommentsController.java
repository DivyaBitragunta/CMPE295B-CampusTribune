package com.ct.controllers;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ct.algorithms.SpamFilter;
import com.ct.model.PostComment;
import com.ct.services.PostCommentService;
import com.ct.services.PostService;

@RestController
@RequestMapping("/comment")
public class PostCommentsController {

	@Autowired
	PostService postService = new PostService();

	@Autowired
	PostCommentService postCommentService = new PostCommentService();

	private static final Logger LOGGER = Logger.getLogger(PostCommentsController.class.getName());

	
	public PostCommentsController() {
		System.out.println("PostCommentsController()");
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<PostComment> createComment(@RequestBody @Valid PostComment postComment) {
		if(SpamFilter.detectSpam(postComment.getCommentContent())){
			LOGGER.info("Comment contains Spam");
			return new ResponseEntity<PostComment>(HttpStatus.PRECONDITION_FAILED );
		}else{
			if (postService.postExists(postComment.getPostId())) {
				postComment = postCommentService.createComment(postComment);
				if (postComment != null) {
					LOGGER.info("Comment created");
					return new ResponseEntity<PostComment>(postComment, HttpStatus.CREATED);
				} else {
					LOGGER.info("Comment create failed");
					return new ResponseEntity<PostComment>(HttpStatus.CONFLICT);
				}
			} else {
				LOGGER.info("Comment create failed");
				return new ResponseEntity<PostComment>(HttpStatus.NOT_FOUND);
			}
		}
	}

	@RequestMapping(value = "/view/{comment_id}/forPost/{post_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PostComment> viewComment(@PathVariable("comment_id") int comment_id,
			@PathVariable("post_id") int post_id) {
		if (postService.postExists(post_id) && postCommentService.commentExists(comment_id)) {
			LOGGER.info("Comment fetched");
			return new ResponseEntity<PostComment>(postCommentService.getComment(comment_id, post_id), HttpStatus.OK);
		} else {
			LOGGER.info("Comment fetch failed");
			return new ResponseEntity<PostComment>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/viewAll/forPost/{post_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<PostComment>> listAllComments(@PathVariable int post_id) {
		if (postService.postExists(post_id)) {
			LOGGER.info("Comments fetched");
			return new ResponseEntity<ArrayList<PostComment>>(postCommentService.getAllComments(post_id),
					HttpStatus.OK);
		} else {
			LOGGER.info("Comments fetch failed");
			return new ResponseEntity<ArrayList<PostComment>>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<PostComment> updateComment(@RequestBody @Valid PostComment postComment) {
		if(SpamFilter.detectSpam(postComment.getCommentContent())){
			LOGGER.info("Comment contains Spam");
			return new ResponseEntity<PostComment>(HttpStatus.PRECONDITION_FAILED );
		}else{
			if (postService.postExists(postComment.getPostId()) && postCommentService.commentExists(postComment.getId())) {
				LOGGER.info("Comment updated");
				return new ResponseEntity<PostComment>(postCommentService.updateComment(postComment), HttpStatus.OK);
			} else {
				LOGGER.info("Comment update failed");
				return new ResponseEntity<PostComment>(HttpStatus.NOT_FOUND);
			}
		}
		

	}

	@RequestMapping(value = "/remove/{comment_id}/forPost/{post_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<PostComment> deleteComment(@PathVariable("comment_id") int comment_id,
			@PathVariable("post_id") int post_id) {
		if (postService.postExists(post_id) && postCommentService.commentExists(comment_id)) {
			postCommentService.deleteComment(comment_id, post_id);
			LOGGER.info("Comment deleted");
			return new ResponseEntity<PostComment>(HttpStatus.OK);
		} else {
			LOGGER.info("Comment delete failed");
			return new ResponseEntity<PostComment>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/report/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<PostComment> reportComment(@PathVariable("user_id") String user_id,@RequestBody @Valid PostComment postComment) {
		if (postCommentService.commentExists(postComment.getId())) {
			LOGGER.info("Comment Reported");
			return new ResponseEntity<PostComment>(postCommentService.reportComment(postComment,user_id), HttpStatus.OK);
		} else {
			LOGGER.info("Comment Report failed");
			return new ResponseEntity<PostComment>(HttpStatus.NOT_FOUND);
		}
	}

}
