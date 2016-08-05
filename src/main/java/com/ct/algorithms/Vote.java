/**
 * 
 */
package com.ct.algorithms;

import java.util.logging.Logger;

/**
 * @author snshr
 *
 */
public class Vote {

	private static final Logger LOGGER = Logger.getLogger(Vote.class.getName());
	
	public static int calculateVoteScore(int voteType,int oldVoteScore){
		int newVoteScore=0; 
		if(voteType==1){
			LOGGER.info("Upvote");
			newVoteScore=oldVoteScore+4;
		}else if(voteType==2){
			LOGGER.info("Remove Upvote");
			newVoteScore=oldVoteScore-4;
		}else if(voteType==3){
			LOGGER.info("Downvote");
			newVoteScore=oldVoteScore-2;
		}else if(voteType==4){
			LOGGER.info("Remove Downvote");
			newVoteScore=oldVoteScore+2;
		}else
			newVoteScore=oldVoteScore;
		return newVoteScore;
	}
}
