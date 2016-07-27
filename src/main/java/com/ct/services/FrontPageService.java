package com.ct.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.dao.PostDAO;
import com.ct.model.Post;
import com.ct.repositories.IPostRepository;
@Service
public class FrontPageService {

	@Autowired
	private IPostRepository postRepo;
	
	/*HashMap<String, ArrayList<String>> userSubcribedCategoryMap = new HashMap();
	userSubcribedCategoryMap.put("CiCr982",{"Sports","Politics","Announcements"});
	public ArrayList<PostDAO> getfrontPageData(String userId) {
		
		System.out.println("IN FRONTPAGE SERVICE");
		return new ArrayList<>(postRepo.findAll());
	}
	
	ArrayList<Post> subscriptionList = new ArrayList();
	subscriptionList = 
	
	//Get List of Categories the subscribed to
	//For each post in the subscription list.. get the  scores using 
	//Sort by vote score
	
	//no of posts per category = length of front page/subscriptioncategories
	//select these many number of posts from each category based on score and add to list.
	
	
*/
}
