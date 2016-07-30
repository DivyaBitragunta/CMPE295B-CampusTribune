package com.ct.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.dao.PostDAO;
import com.ct.dao.UserDAO;
import com.ct.repositories.IPostCommentRepository;
import com.ct.repositories.IPostRepository;
import com.ct.repositories.IUserDetailsRepository;
@Service
public class FrontPagePostService {

	@Autowired
	private IPostRepository postRepo;
	
	@Autowired
	private IUserDetailsRepository userRepo;
	
	@Autowired
	private IPostCommentRepository postCommentRepo;
	
	public ArrayList<PostDAO> getfrontPagePostData(String userId) {
		ArrayList<PostDAO> postlist = new ArrayList<PostDAO>();
		UserDAO user = userRepo.findById(userId);
		ArrayList<String>  userSubscription = user.getSubscriptionList();
		for(String category : userSubscription) {
			List<PostDAO> top2Posts = findTopPostsForCategory(category,user.getUniversity());
		}
		return postlist;
	}

	public List<PostDAO> findTopPostsForCategory(String category, String university) {
		
		HashMap<Integer, Integer> postsScoreMap = new HashMap<Integer, Integer>();
		//Accumulate most recent posts based on last edited on
		//Will change to Top100 once data set up is done
		List<PostDAO> listOfPosts =postRepo.findTop10ByCategoryAndUniversityOrderByLastEditedOnDesc(category,university);

		for(PostDAO post:listOfPosts){
			int voteScore=post.getVoteScore();
			int commentScore = findCommentScore(post.getId());
			int followScore = post.getFollowCount();
			int age = findAgeOfPost(post.getId());
			int cumulativeScore=age-voteScore+commentScore+followScore;
			postsScoreMap.put(post.getId(), cumulativeScore);
		}
		
		return listOfPosts;
	}

	private int findAgeOfPost(int id) {
		DateTime postCreatedOn = new DateTime((postRepo.findById(id)).getCreatedOn());
		DateTime currentDateTime = new DateTime(DateTimeZone.UTC);
		Hours hours = Hours.hoursBetween(postCreatedOn, currentDateTime);
		Period p = new Period(postCreatedOn, currentDateTime);
		int hoursPeriod = p.getHours();
		int minutes = p.getMinutes();
		System.out.println("No of HOURS"+hoursPeriod);
		System.out.println("No of MINUTES"+minutes);
		System.out.println("");
		return hours.getHours();
		
	}


	private int findCommentScore(int id) {
		return (postCommentRepo.findByPostId(id)).size();
	}


	
	
	
	//Get List of Categories the subscribed to
	//For each post in the subscription list.. get the  scores
	//Sort by vote score
	
	//no of posts per category = length of front page/subscriptioncategories
	//select these many number of posts from each category based on score and add to list.
	
	

}
