/**
 * 
 */
package com.ct.algorithms;

import java.util.logging.Logger;

/**
 * @author snshr
 *
 */
public class Report {

	private static final Logger LOGGER = Logger.getLogger(Report.class.getName());
	
	public static int updateReportScore(int oldReportScore){
		LOGGER.info("Calculating new report score");
		return ++oldReportScore;
	}
	
	public static boolean removeContent(int reportScore){
		if(reportScore>=10)
			return true;
		else
			return false;
	}
}
