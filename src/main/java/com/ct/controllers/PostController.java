package com.ct.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.model.Post;
import com.ct.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	public PostController() {
		System.out.println("PostController()");
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) {
		post = postService.createPost(post);
		if (post != null) {
			return new ResponseEntity<Post>(post, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Post>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/get/{post_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Post> viewPost(@PathVariable("post_id") int postId) {
		if (postService.postExists(postId))
			return new ResponseEntity<Post>(postService.getPost(postId), HttpStatus.OK);
		else {

			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> updatePost(@RequestBody @Valid Post post) {
		if (postService.postExists(post.getId())) {
			return new ResponseEntity<Post>(postService.updatePost(post), HttpStatus.OK);
		} else {
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete/{post_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deletePost(@PathVariable("post_id") int postId) {
		if (postService.postExists(postId)) {
			postService.deletePost(postId);
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<Post>> getPostsForCategory(@PathVariable String category) {
		ArrayList<Post> posts = postService.getPostsForCategory(category);
		if (posts != null && posts.size() > 0) {
			return new ResponseEntity<ArrayList<Post>>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<ArrayList<Post>>(HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/vote/{voteType}/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> votePost(@PathVariable("voteType") int voteType,@PathVariable("user_id") int user_id, @RequestBody @Valid Post post) {
		if (postService.postExists(post.getId())) {
			return new ResponseEntity<Post>(postService.votePost(voteType,user_id,post), HttpStatus.OK);
		} else {
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/report/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> reportPost(@PathVariable("user_id") int user_id, @RequestBody @Valid Post post) {
		if (postService.postExists(post.getId())) {
			return new ResponseEntity<Post>(postService.reportPost(user_id,post), HttpStatus.OK);
		} else {
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/follow/{post_id}/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<String> reportPost(@PathVariable("post_id") int post_id,@PathVariable("user_id") int user_id) {
		if (postService.postExists(post_id)) {
			postService.followPost(user_id,post_id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
}