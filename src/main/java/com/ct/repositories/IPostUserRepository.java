/**
 * 
 */
package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.PostUserDAO;

/**
 * @author snshr
 *
 */
public interface IPostUserRepository extends MongoRepository<PostUserDAO, String> {
	public PostUserDAO findByUser(String userId);
}
