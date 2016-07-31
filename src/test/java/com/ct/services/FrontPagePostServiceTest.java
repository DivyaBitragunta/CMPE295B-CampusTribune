package com.ct.services;

import java.util.ArrayList;
import java.util.List;


import org.junit.Test;

import com.ct.dao.PostDAO;

public class FrontPagePostServiceTest {

	FrontPagePostService underTest = new FrontPagePostService();
	@Test
	public void testFindTopPosts() throws Exception {
	
		int[] scoreArray = {50,60,30,79,-5,199};
		List<PostDAO> listOfPosts = new ArrayList<PostDAO>();
		PostDAO postDao1 = new PostDAO();
		postDao1.setId(1);
		listOfPosts.add(postDao1);
		PostDAO postDao2 = new PostDAO();
		postDao2.setId(2);
		listOfPosts.add(postDao2);
		PostDAO postDao3 = new PostDAO();
		postDao3.setId(3);
		listOfPosts.add(postDao3);
		PostDAO postDao4 = new PostDAO();
		postDao4.setId(4);
		listOfPosts.add(postDao4);
		PostDAO postDao5 = new PostDAO();
		postDao5.setId(5);
		listOfPosts.add(postDao5);
		PostDAO postDao6 = new PostDAO();
		postDao6.setId(6);
		listOfPosts.add(postDao6);
		List<PostDAO> topPosts = underTest.getTop4(listOfPosts, scoreArray);
		for(PostDAO post: topPosts) {
			System.out.println(post.getId());
		}
		org.junit.Assert.assertEquals(6, topPosts.get(0).getId());
		org.junit.Assert.assertEquals(4, topPosts.get(1).getId());
		org.junit.Assert.assertEquals(2, topPosts.get(2).getId());
		org.junit.Assert.assertEquals(1, topPosts.get(3).getId());

		
	}

}
