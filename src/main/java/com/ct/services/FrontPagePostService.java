package com.ct.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
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
		ArrayList<String> userSubscription = user.getSubscriptionList();
		for (String category : userSubscription) {
			List<PostDAO> topPosts = findTopPostsForCategory(category,
					user.getUniversity());
			for(PostDAO post:topPosts){
				postlist.add(post);
			}
				
		}
		List<PostDAO> listOfAlerts= new ArrayList<PostDAO>();
		try{
			listOfAlerts= postRepo.findTop3ByIsAlertOrderByCreatedOnDesc();
		}catch (Exception e){
			System.out.println("No Alerts in the repo!");
		}
		if(listOfAlerts!=null){
			for(PostDAO alert:listOfAlerts){
				postlist.add(alert);
			}
		}	
		
		return postlist;
	}

	public List<PostDAO> findTopPostsForCategory(String category,
			String university) {
		List<PostDAO> listOfPosts = postRepo
				.findTop10ByCategoryAndUniversityOrderByLastEditedOnDesc(
						category, university);
		int[] scoreArray = new int[listOfPosts.size()];
		int listIndex = 0;
		for (PostDAO post : listOfPosts) {

			int voteScore = post.getVoteScore();
			int commentScore = findCommentScore(post.getId());
			int followScore = post.getFollowCount();
			int age = findAgeOfPost(post.getId());
			int cumulativeScore = voteScore + commentScore + followScore - age;
			scoreArray[listIndex++] = cumulativeScore;
			System.out.println("SCORE"+cumulativeScore);

		}

		return getTop4(listOfPosts, scoreArray);
	}

	public List<PostDAO> getTop4(List<PostDAO> listOfPosts, int[] scoreArray) {
		List<PostDAO> topPosts = new ArrayList<PostDAO>();
		if(listOfPosts.size()<4){
			return listOfPosts;
		}
		for (int i = 0; i < 4; i++) {
			int highScore = Integer.MIN_VALUE;
			int highScoreIndex = i;
			for (int j = i; j < scoreArray.length; j++) {
				if (scoreArray[j] > highScore) {
					highScore = scoreArray[j];
					highScoreIndex = j;
				}
			}
			int temp = scoreArray[i];
			scoreArray[i] = highScore;
			scoreArray[highScoreIndex] = temp;
			topPosts.add(listOfPosts.get(highScoreIndex));
			PostDAO tempPost = listOfPosts.get(i);
			listOfPosts.set(i, listOfPosts.get(highScoreIndex));
			listOfPosts.set(highScoreIndex, tempPost);

		}
		return topPosts;
	}

	private int findAgeOfPost(int id) {
		DateTime postCreatedOn = new DateTime(
				(postRepo.findById(id)).getCreatedOn());
		DateTime currentDateTime = new DateTime(DateTimeZone.UTC);
		Hours hours = Hours.hoursBetween(postCreatedOn, currentDateTime);
		return hours.getHours();

	}

	private int findCommentScore(int id) {
		return (postCommentRepo.findByPostId(id)).size();
	}


}
