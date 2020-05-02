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
	
	public Map<String, CountedWord> createCountedWordsMapFromFile(String fileName) {
		Map<String, CountedWord> countedWordsByWord = null;
		try (InputStream is = new FileInputStream(fileName)) {
			countedWordsByWord = createCountedWordsMap(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
		return countedWordsByWord;
	}
	
	public Map<String, CountedWord> createDefaultCountedWordsMap() {
		Map<String, CountedWord> countedWordsByWord = null;
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_FILE_TO_COUNT)) {
			countedWordsByWord = createCountedWordsMap(is);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
		return countedWordsByWord;
	}
	
	private Map<String, CountedWord> createCountedWordsMap(InputStream is) {
		Map<String, CountedWord> countedWordsByWord = new HashMap<>();
		if (stopWords == null) {
			stopWords = new HashSet<>();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split("[^\\w-’']+");
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
		return countedWordsByWord;
	}
	
	public List<CountedWord> getTop100CountedWordsFromFile(String fileName) {
		return getTop100CountedWords(createCountedWordsMapFromFile(fileName));
	}
	
	public List<CountedWord> getTop100CountedWordsFromDefaultFile() {
		return getTop100CountedWords(createDefaultCountedWordsMap());
	}
	
	public List<CountedWord> getTop100CountedWords(Map<String, CountedWord> countedWordsByWord) {
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
	
	/*
	TODO: 
		- Documentation:
			- usage
		
			- default behavior:
				- starts at first line of file
				- order is based on count, then alphabetical order
				- plural handling
				- case handling
				- hyphinated words
				
			- apostrophe and hyphen problems:
				- no leading or trailing spaces on hyphens or apostrophes in provided text
				- input file uses - and — differently (important for handling of hyphinated words)
				- possessive nouns and contractions counted as separate words
				- multiple hyphens with no spaces
				- matches hyphens and apostrophes by themselves
			
			- custom use:
				- command line arguments
				
			
			
		- stretch: plural logic? maybe use argument, have it both ways
	*/

}
