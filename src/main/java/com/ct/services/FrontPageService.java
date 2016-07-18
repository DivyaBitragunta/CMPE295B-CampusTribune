package com.ct.services;

import java.util.ArrayList;
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

	public ArrayList<PostDAO> getfrontPageData(String userId) {
		
		System.out.println("IN FRONTPAGE SERVICE");
		return new ArrayList<>(postRepo.findAll());
	}

}
