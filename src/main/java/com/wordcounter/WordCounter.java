package com.wordcounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	private static final String DEFAULT_STOP_WORDS = "stop-words.txt";
	private static final String DEFAULT_FILE_TO_COUNT = "mobydick.txt";
	
	public void populateStopWordsFromFile(String fileName) {
		try (InputStream is = new FileInputStream(fileName)) {
			populateStopWords(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public void populateDefaultStopWords() {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_STOP_WORDS)) {
			populateStopWords(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	private void populateStopWords(InputStream is) {
		stopWords = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
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
	
	public void populateCountedWordsMapFromFile(String fileName) {
		try (InputStream is = new FileInputStream(fileName)) {
			populateCountedWordsMap(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public void populateDefaultCountedWordsMap() {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_FILE_TO_COUNT)) {
			populateCountedWordsMap(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	private void populateCountedWordsMap(InputStream is) {
		countedWordsByWord = new HashMap<>();
		if (stopWords == null) {
			stopWords = new HashSet<>();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
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
	
	public List<CountedWord> getTop100CountedWordsFromFile(String fileName) {
		populateCountedWordsMapFromFile(fileName);
		return getTop100CountedWords();
	}
	
	public List<CountedWord> getTop100CountedWordsFromDefaultFile() {
		populateDefaultCountedWordsMap();
		return getTop100CountedWords();
	}
	
	private List<CountedWord> getTop100CountedWords() {
		List<CountedWord> top100 = new ArrayList<>();
		for (CountedWord cw : countedWordsByWord.values()) {
			top100.add(cw);
		}
		Collections.sort(top100);
		if (top100.size() > 100) {
			return top100.subList(0, 100);
		} else {
			return top100;
		}
	}
	
	public Set<String> getStopWords() {
		return stopWords;
	}
	
	public Map<String, CountedWord> getCountedWordsByWord() {
		return countedWordsByWord;
	}
	
	
	/*
	TODO: 
		- Documentation
		- count hyphenated words as one
		- ensure alphabetic sorting for same count
		
		- stretch: plural logic? maybe use argument, have it both ways
	   
	   
	   questions: 
		- where to start in mobydick.txt? whole file? after ***start... line?
		- hyphenated words?
		- case sensitive?
		- words with same count?
		- plural?
	*/

}
