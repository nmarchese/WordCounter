package com.wordcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordCounter {
	
	private Set<String> stopWords;
	private Map<String, CountedWord> countedWordsByWord;
	
	public void populateStopWordsFromFile(String filePath) {
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
	
	public void populateCountedWordsMapFromFile(String filePath) {
		countedWordsByWord = new HashMap<>();
		if (stopWords == null) {
			stopWords = new HashSet<>();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split("\\W+");
				for (String word : words) {
					if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
						CountedWord cw = countedWordsByWord.get(word.toLowerCase());
						if (cw == null) {
							cw = new CountedWord(word.toLowerCase());
						} else {
							cw.iterateCount();
						}
						countedWordsByWord.put(word.toLowerCase(), cw);
					}
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public List<CountedWord> getTop100CountedWordsFromFile(String filePath) {
		populateCountedWordsMapFromFile(filePath);
		List<CountedWord> top100 = new ArrayList<>();
		for (CountedWord cw : countedWordsByWord.values()) {
			top100.add(cw);
		}
		Collections.sort(top100);
		return top100.subList(0, 100);
	}
	
	public Set<String> getStopWords() {
		return stopWords;
	}
	
	public Map<String, CountedWord> getCountedWordsByWord() {
		return countedWordsByWord;
	}
	
	
	/*
	TODO: 
	   questions: 
		- where to start in mobydick.txt? whole file? after ***start... line?
		- hyphenated words?
		- case sensitive?
		- words with same count?
		- plural?
	*/

}
