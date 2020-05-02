package com.wordcounter;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

public class WordCounterTest {
	
//	private String stopWordsFile = "stop-words.txt";
//	private String fileToCountFile = "mobydick.txt";
	private WordCounter counter;
	
	@Before
	public void setUp() {
		counter = new WordCounter();
	}
	
	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void test_populateDefaultStopWords_populates_stopWords_set() {
		counter.populateDefaultStopWords();
		Set<String> stopWords = counter.getStopWords();
		assertNotNull(stopWords);
		assertTrue(stopWords.contains("about"));
	}
	
	@Test
	public void test_populateDefaultCountedWordsMap_populates_countedWordsByWord_map() {
		counter.populateDefaultCountedWordsMap();
		Map<String, CountedWord> countedWordsMap = counter.getCountedWordsByWord();
		assertNotNull(countedWordsMap);
		assertTrue(countedWordsMap.containsKey("whale"));
		assertEquals(countedWordsMap.get("whale").getCount(), 1244);
	}
	
	@Test
	public void test_getTop100CountedWordsFromFile_returns_list_of_100_words() {
		List<CountedWord> words = counter.getTop100CountedWordsFromDefaultFile();
		assertNotNull(words);
		assertEquals(words.size(), 100);
	}
	
}
