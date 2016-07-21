/**
 * 
 */
package com.ct.algorithms;

/**
 * @author snshr
 *
 */
public class WordStats {
	private int spamCount;
	private int nonSpamCount;
	private double probabilityOfWord;
	
	
	public WordStats(int spamCount,int nonSpamCount){
		this.spamCount=spamCount;
		this.nonSpamCount=nonSpamCount;
	}
	
	public int getSpamCount() {
		return spamCount;
	}
	public void setSpamCount(int spamCount) {
		this.spamCount = spamCount;
	}
	public int getNonSpamCount() {
		return nonSpamCount;
	}
	public void setNonSpamCount(int nonSpamCount) {
		this.nonSpamCount = nonSpamCount;
	}
	public double getProbability() {
		return probabilityOfWord;
	}
	public void setProbability(double probability) {
		this.probabilityOfWord = probability;
	}
}
