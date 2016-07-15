/**
 * 
 */
package com.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ct.dao.UserActionsForPostDAO;

/**
 * @author snshr
 *
 */
public interface IUserActionsForPostRepository extends MongoRepository<UserActionsForPostDAO, Integer> {
	public UserActionsForPostDAO findByUseridAndPostid(int userid, int postid);
}
