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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.algorithms.SpamFilter;
import com.ct.model.Post;
import com.ct.model.PostUser;
import com.ct.notifications.NotificationSystem;
import com.ct.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	private static final Logger LOGGER = Logger.getLogger(PostController.class.getName());

	public PostController() {
		System.out.println("PostController()");
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) {
		if(SpamFilter.detectSpam(post.getHeadline())){
			LOGGER.info("Post contains Spam");
			return new ResponseEntity<Post>(HttpStatus.PRECONDITION_FAILED );
		}if(SpamFilter.detectSpam(post.getContent())){
			LOGGER.info("Post contains Spam");
			return new ResponseEntity<Post>(HttpStatus.PRECONDITION_FAILED );
		}else{
			post = postService.createPost(post);
			if (post != null) {
				LOGGER.info("Post created");
				return new ResponseEntity<Post>(post, HttpStatus.CREATED);
			} else {
				LOGGER.info("Post create failed");
				return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
			}
		}
		
	}

	@RequestMapping(value = "/view/{post_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Post> viewPost(@PathVariable("post_id") int postId) {
		if (postService.postExists(postId)){
			LOGGER.info("Post fetched");
			return new ResponseEntity<Post>(postService.getPost(postId), HttpStatus.OK);
		}else {
			LOGGER.info("Post fetch failed");
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> updatePost(@RequestBody @Valid Post post) {
		if(SpamFilter.detectSpam(post.getHeadline())){
			LOGGER.info("Post contains Spam");
			return new ResponseEntity<Post>(HttpStatus.PRECONDITION_FAILED );
		}if(SpamFilter.detectSpam(post.getContent())){
			LOGGER.info("Post contains Spam");
			return new ResponseEntity<Post>(HttpStatus.PRECONDITION_FAILED );
		}else{
			if (postService.postExists(post.getId())) {
				Post postRet = new Post();
				postRet=postService.updatePost(post);
				LOGGER.info("Post updated");
				String str[]=new String[2];
				String msg  = postRet.getHeadline()+" is updated";
	            str[0]=msg;
				NotificationSystem ns = new NotificationSystem();
				ns.sendNotifications(str);
				LOGGER.info("Post update notification sent");
				return new ResponseEntity<Post>(postRet, HttpStatus.OK);
			}
			else {
				LOGGER.info("Post update failed");
				return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
			}
		}
	}

	@RequestMapping(value = "/remove/{post_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deletePost(@PathVariable("post_id") int postId) {
		if (postService.postExists(postId)) {
			postService.deletePost(postId);
			LOGGER.info("Post deleted");
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			LOGGER.info("Post delete failed");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getByCategory/{category}/{university}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<Post>> getPostsForCategory(@PathVariable String category,@PathVariable String university) {
		ArrayList<Post> posts = postService.getPostsForCategory(category,university);
		if (posts != null && posts.size() > 0) {
			LOGGER.info("Posts fetched");
			return new ResponseEntity<ArrayList<Post>>(posts, HttpStatus.OK);
		} else {
			LOGGER.info("Posts fetch failed");
			return new ResponseEntity<ArrayList<Post>>(HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/vote/{voteType}/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> votePost(@PathVariable("voteType") int voteType,@PathVariable("user_id") String user_id, @RequestBody @Valid Post post) {
		if (postService.postExists(post.getId())) {
			LOGGER.info("Post voted");
			return new ResponseEntity<Post>(postService.votePost(voteType,user_id,post), HttpStatus.OK);
		} else {
			LOGGER.info("Post vote failed");
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/report/byUser/{user_id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Post> reportPost(@PathVariable("user_id") String user_id, @RequestBody @Valid Post post) {
		if (postService.postExists(post.getId())) {
			LOGGER.info("Post reported");
			return new ResponseEntity<Post>(postService.reportPost(user_id,post), HttpStatus.OK);
		} else {
			LOGGER.info("Post report failed");
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/follow/byUser/{user_id}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> reportPost(@RequestBody @Valid Post post,@PathVariable("user_id") String user_id) {
		if (postService.postExists(post.getId())) {
			postService.followPost(user_id,post.getId());
			LOGGER.info("Post followed");
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			LOGGER.info("Post follow failed");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getUserActions/{user_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PostUser> getUserActioForPosts(@PathVariable("user_id") String user_id) {
		if (postService.userActionExists(user_id)) {
			LOGGER.info("User actions fetched");
			return new ResponseEntity<PostUser>(postService.getUserActioForPosts(user_id),HttpStatus.OK);
		} else {
			LOGGER.info("User actions fetch failed");
			return new ResponseEntity<PostUser>(HttpStatus.NOT_FOUND);
		}
	}
	
}
