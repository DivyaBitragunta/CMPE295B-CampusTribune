/**
 * 
 */
package com.ct.algorithms;

/**
 * @author snshr
 *
 */
public class Vote {

	public static int calculateVoteScore(int voteType,int oldVoteScore){
		int newVoteScore=0; 
		if(voteType==1)
			newVoteScore=oldVoteScore+4;
		else if(voteType==2)
			newVoteScore=oldVoteScore-4;
		else if(voteType==3)
			newVoteScore=oldVoteScore-2;
		else if(voteType==4)
			newVoteScore=oldVoteScore+2;
		else
			newVoteScore=oldVoteScore;
		return newVoteScore;
	}
}
