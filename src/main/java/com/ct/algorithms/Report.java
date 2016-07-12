/**
 * 
 */
package com.ct.algorithms;

/**
 * @author snshr
 *
 */
public class Report {

	public static int updateReportScore(int oldReportScore){
		return ++oldReportScore;
	}
	
	public static boolean removeContent(int reportScore){
		if(reportScore>=10)
			return true;
		else
			return false;
	}
}
