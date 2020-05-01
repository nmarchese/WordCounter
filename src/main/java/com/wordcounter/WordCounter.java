package com.wordcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordCounter {
	
	Set<String> stopWords;
	Map<String, Integer> countByWords;
	
	public void setStopWordsFromFile(String filePath) {
		stopWords = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0 && line.charAt(0) != '#') {
					stopWords.add(line.toLowerCase());
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public void populateCountByWordsMapFromFile(String filePath) {
		countByWords = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split("\\W+");
				for (String word : words) {
					if (!stopWords.contains(word.toLowerCase())) {
						Integer count = countByWords.get(word.toLowerCase());
						countByWords.put(word.toLowerCase(), (count != null ? count + 1 : 1));
					}
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public Map<String, Integer> getTop100WordsAndCounts() {
		Map<String, Integer> top100 = new HashMap<>();
		
		return top100;
	}
	
	
	
	/*
	TODO: 
	   questions: 
		- where to start in mobydick.txt? whole file? after ***start... line?
		- hyphenated words?
		- case sensitive?
	*/

}
