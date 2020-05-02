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
			ioe.printStackTrace();
		}
	}
	
	public void populateDefaultStopWords() {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_STOP_WORDS)) {
			populateStopWords(is);
		} catch (IOException ioe) {
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
			ioe.printStackTrace();
		}
	}
	
	public Map<String, CountedWord> createCountedWordsMapFromFile(String fileName) {
		Map<String, CountedWord> countedWordsByWord = null;
		try (InputStream is = new FileInputStream(fileName)) {
			countedWordsByWord = createCountedWordsMap(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return countedWordsByWord;
	}
	
	public Map<String, CountedWord> createDefaultCountedWordsMap() {
		Map<String, CountedWord> countedWordsByWord = null;
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(DEFAULT_FILE_TO_COUNT)) {
			countedWordsByWord = createCountedWordsMap(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return countedWordsByWord;
	}
	
	private Map<String, CountedWord> createCountedWordsMap(InputStream is) {
		Map<String, CountedWord> countedWordsByWord = new HashMap<>();
		if (stopWords == null) {
			stopWords = new HashSet<>();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				
				/* Commented code left in project to potentially be used later for different handling of apostrophes/single quotes.
				// This code when uncommented would result in treating left and right single quotes (used as apostrophes in mobydick.txt)
				// as actual apostrophes, enabling the counting of words like "don't" and avoiding the counting of "ll" as a separate word
				// instead of "he'll". Unfortunately, in this state this also results in the inability to properly handle actual left and 
				// right single quotes. Specifically, this results in serious mishandeling of lines like:
				      mad,—'I want to use him.' 'Take him,' says the governor—and
				// where "'I" is a word and ".'" results in "'" being counted as a word, etc.
					
				// sanitize left and right single quotes to be treated as apostrophes
				line = line.replaceAll("\u2019", "'");
				line = line.replaceAll("\u2018", "'");
				 */
				
				
				String[] words = line.split("[^\\w-']+");
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
		- stretch goals: 
			- toggles for plural handling, case sensitive, hyphen/apostrophe handling
			- build UI and hook up so that output is printed to UI and custom files can be accepted from UI
	*/

}
