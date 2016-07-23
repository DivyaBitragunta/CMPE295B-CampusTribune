/**
 * 
 */
package com.ct.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.PostDAO;

/**
 * @author snshr
 *
 */
public interface IPostRepository extends MongoRepository<PostDAO, Integer> {

	public PostDAO findById(Integer id);

	public ArrayList<PostDAO> findTop10ByCategoryOrderByLastEditedOnDesc(String category);
	
	
}
