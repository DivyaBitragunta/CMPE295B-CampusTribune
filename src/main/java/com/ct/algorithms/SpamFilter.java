/**
 * 
 */
package com.ct.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import com.ct.controllers.PostController;

public class SpamFilter {

	private static final String WORDS_FILE = "/common.txt";
	private static final String NON_SPAM_FILE = "/nonspam.txt";
	private static final String SPAM_FILE = "/spam.txt";
	private static final Logger LOGGER = Logger.getLogger(SpamFilter.class.getName());

	private static double spamContentCount = 0;
	private static double nonSpamContentCount = 0;
	public static HashMap<String, WordStats> wordStatsMap = new HashMap<String, WordStats>();
	private static ArrayList<String> allWords = new ArrayList<String>();
	private static double totalSpamProbability = 0;
	private static double totalNonSpamProbability = 0;

	/*public static void main(String[] args) {
		train();
		if (detectSpam("Download free movie from below"))
			System.out.println("Is spam");
		else
			System.out.println("Is not spam");
	}*/

	public static void train() {
		LOGGER.info("Training Spam Filter");
		readWords();
		readCollectedSpamWords();
		readCollectedNonSpamWords();
		calculateCollectedWordsProbability();
		totalSpamProbability = spamContentCount / (spamContentCount + nonSpamContentCount);
		totalNonSpamProbability = nonSpamContentCount / (spamContentCount + nonSpamContentCount);
		LOGGER.info("Trianing spam filter completed");
	}

	public static boolean detectSpam(String inputString) {
		LOGGER.info("Detecting content for spam");
		if (inputString != null && inputString.length() > 0) {
			String[] inputStringWords = inputString.split("\\s");
			double spamProb = calculateSpamProbabilityForString(inputStringWords);
			System.out.println(spamProb);

			double nonSpamProb = calculateNonSpamProbabilityForString(inputStringWords);
			System.out.println(nonSpamProb);
			if (spamProb > nonSpamProb){
				LOGGER.info("Content is spam");
				return true;
			}else{
				LOGGER.info("Content is not spam");
				return false;
			}
		} else {
			LOGGER.info("Content is not spam");
			return false;
		}
	}

	private static void readWords() {
		try {
			InputStream in = SpamFilter.class.getResourceAsStream(WORDS_FILE);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 400000);
			String word = bufferedReader.readLine();
			while (word != null) {
				if (word.trim().length() > 0)
					allWords.add(word);
				word = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			System.err.println("Error reading file : " + e.getMessage());
		}
	}

	private static void readCollectedNonSpamWords() {
		try {
			InputStream in = SpamFilter.class.getResourceAsStream(NON_SPAM_FILE);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 400000);
			String nonSpamWord = bufferedReader.readLine();
			while (nonSpamWord != null) {
				if (nonSpamWord.trim().length() > 0) {
					String nsWord = nonSpamWord.toLowerCase();
					if (nsWord.length() > 2 && !allWords.contains(nsWord)) {
						nonSpamContentCount++;
						// System.out.println(nonSpamContentCount);
						if (wordStatsMap.containsKey(nsWord)) {
							wordStatsMap.get(nsWord).setNonSpamCount(wordStatsMap.get(nsWord).getNonSpamCount() + 1);

							// System.out.println(wordStatsMap.get(nsWord).getNonSpamCount());
						} else {
							wordStatsMap.put(nsWord, new WordStats(0, 1));

							// System.out.println(wordStatsMap.get(nsWord).getNonSpamCount());
						}
					}
				}
				nonSpamWord = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			System.err.println("Error reading file : " + e.getMessage());
		}
	}

	private static void readCollectedSpamWords() {
		try {
			InputStream in = SpamFilter.class.getResourceAsStream(SPAM_FILE);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 400000);
			String spamWord = bufferedReader.readLine();
			while (spamWord != null) {
				if (spamWord.trim().length() > 0) {
					String sWord = spamWord.toLowerCase();
					if (sWord.length() > 2 && !allWords.contains(sWord)) {
						// System.out.println(spamContentCount);
						spamContentCount++;
						if (wordStatsMap.containsKey(sWord)) {
							wordStatsMap.get(sWord).setSpamCount(wordStatsMap.get(sWord).getSpamCount() + 1);
							// System.out.println(wordStatsMap.get(sWord).getSpamCount());
						} else {
							wordStatsMap.put(sWord, new WordStats(1, 0));
							// System.out.println(wordStatsMap.get(sWord).getSpamCount());
						}
					}
				}
				spamWord = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			System.err.println("Error reading file : " + e.getMessage());
		}
	}

	private static void calculateCollectedWordsProbability() {
		Set<String> Words = wordStatsMap.keySet();
		for (String word : Words) {
			WordStats wStats = wordStatsMap.get(word);
			double spamProbability = wStats.getSpamCount() / spamContentCount;
			double nonSpamProbability = wStats.getNonSpamCount() / nonSpamContentCount;
			/*
			 * if(wStats.getSpamCount()==0)
			 * wStats.setProbability(nonSpamProbability / (spamProbability +
			 * nonSpamProbability)); else
			 */
			wStats.setProbability(spamProbability / (spamProbability + nonSpamProbability));
			// printNodeValues(word, wStats);
		}
	}

	private static double calculateSpamProbabilityForString(String[] inputWords) {
		double spamVal = totalSpamProbability;
		// System.out.println(totalSpamProbability);
		if (inputWords.length > 0) {
			for (String wrd : inputWords) {
				String w = wrd.trim().toLowerCase();
				if (wordStatsMap.containsKey(w)) {
					spamVal = spamVal * wordStatsMap.get(w).getProbability();
				} else {
					spamVal = spamVal * (1 / totalSpamProbability);
				}
				// System.out.println(spamVal);
			}

			return spamVal;

		} else {
			return 0;
		}
	}

	private static double calculateNonSpamProbabilityForString(String[] inputWords) {
		double nonSpamVal = totalNonSpamProbability;
		// System.out.println(totalNonSpamProbability);
		if (inputWords.length > 0) {
			for (String wrd : inputWords) {
				String w = wrd.trim().toLowerCase();
				if (wordStatsMap.containsKey(w)) {
					nonSpamVal = nonSpamVal * wordStatsMap.get(w).getProbability();
				} else {
					nonSpamVal = nonSpamVal * (1 / totalNonSpamProbability);
				}
				// System.out.println(nonSpamVal);
			}
			return nonSpamVal;

		} else {
			return 0;
		}
	}

	/*private static void printNodeValues(String key, WordStats wStats) {
		System.out.println(key);
		System.out.println("The values are " + wStats.getSpamCount() + " " + wStats.getNonSpamCount() + " "
				+ wStats.getProbability());
	}*/

}