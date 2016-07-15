/**
 * 
 */
package com.ct.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.PostCommentDAO;

/**
 * @author snshr
 *
 */
public interface IPostCommentRepository extends MongoRepository<PostCommentDAO, Integer> {

	public PostCommentDAO findByIdAndPostId(Integer id, Integer postId);
	
	public ArrayList<PostCommentDAO> findByPostId(Integer postId);
	
}
