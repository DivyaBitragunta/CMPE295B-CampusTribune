/**
 * 
 */
package com.ct.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.PostDAO;

/**
 * @author snshr
 *
 */
public interface IPostRepository extends MongoRepository<PostDAO, Integer> {

	public PostDAO findById(Integer id);


	public ArrayList<PostDAO> findByCategoryAndUniversityOrderByLastEditedOnDesc(String category,String university);
	
	public ArrayList<PostDAO> findByCategory(String category);
	public ArrayList<PostDAO> findByCategoryAndUniversity(String category,String university);

	public ArrayList<PostDAO> findTop10ByCategoryOrderByLastEditedOnDesc(String category);

	public ArrayList<PostDAO> findByUniversity(String university);


	public List<PostDAO> findTop10ByCategoryAndUniversity(String category, String university);


	public List<PostDAO> findTop10ByCategoryAndUniversityOrderByLastEditedOnDesc(
			String category, String university);


	public List<PostDAO> findTop3ByIsAlertOrderByCreatedOnDesc();



	
	
}
