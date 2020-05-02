package com.wordcounter;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

public class WordCounterTest {
	
	private WordCounter counter;
	
	@Before
	public void setUp() {
		counter = new WordCounter();
	}
	
	@Test
	public void test_that_tests_test() {
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
	public void test_populateStopWordsFromFile_populates_stopWords_from_file() {
		counter.populateStopWordsFromFile("src\\main\\resources\\stop-words.txt");
		Set<String> stopWords = counter.getStopWords();
		assertNotNull(stopWords);
		assertTrue(stopWords.contains("about"));
	}
	
	@Test
	public void test_createDefaultCountedWordsMap_populates_countedWordsByWord_map() {
		Map<String, CountedWord> countedWordsMap = counter.createDefaultCountedWordsMap();
		assertNotNull(countedWordsMap);
		assertTrue(countedWordsMap.containsKey("whale"));
		assertEquals(1114, countedWordsMap.get("whale").getCount());
	}
	
	@Test
	public void test_createCountedWordsMapFromFile_populates_countedWordsByWord_map() {
		Map<String, CountedWord> countedWordsMap = counter.createCountedWordsMapFromFile("src\\main\\resources\\mobydick.txt");
		assertNotNull(countedWordsMap);
		assertTrue(countedWordsMap.containsKey("whale"));
		assertEquals(1114, countedWordsMap.get("whale").getCount());
	}
	
	@Test
	public void test_hyphinated_word_counted_as_unique_word() {
		Map<String, CountedWord> countedWordsMap = counter.createCountedWordsMapFromFile("src\\main\\resources\\mobydick.txt");
		assertNotNull(countedWordsMap);
		assertTrue(countedWordsMap.containsKey("whale-ship"));
		assertEquals(28, countedWordsMap.get("whale-ship").getCount());
	}
	
	/* commented code left in project to potentially be used later for different handling of apostrophes/single quotes
	
	@Test
	public void test_word_with_apostrophe_counted_as_unique_word() {
		Map<String, CountedWord> countedWordsMap = counter.createCountedWordsMapFromFile("src\\main\\resources\\mobydick.txt");
		assertNotNull(countedWordsMap);
		assertTrue(countedWordsMap.containsKey("he'll"));
		assertEquals(18, countedWordsMap.get("he'll").getCount());
	}
	
	*/
	
	@Test
	public void test_getTop100CountedWordsFromDefaultFile_returns_list_of_100_words() {
		List<CountedWord> words = counter.getTop100CountedWordsFromDefaultFile();
		assertNotNull(words);
		assertEquals(100, words.size());
	}

	@Test
	public void test_getTop100CountedWords_returns_list_sorted_by_count() {
		CountedWord word1 = new CountedWord("test1");
		CountedWord word2 = new CountedWord("test2");
		word1.iterateCount();
		Map<String, CountedWord> countedWordsMap = new HashMap<>();
		countedWordsMap.put(word1.getWord(), word1);
		countedWordsMap.put(word2.getWord(), word2);
		
		List<CountedWord> words = counter.getTop100CountedWords(countedWordsMap);
		assertNotNull(words);
		assertEquals(word1, words.get(0));
		assertEquals(word2, words.get(1));
	}
	
	@Test
	public void test_getTop100CountedWords_returns_list_sorted_alphabetically_when_count_is_equal() {
		CountedWord word1 = new CountedWord("testB");
		CountedWord word2 = new CountedWord("testA");
		Map<String, CountedWord> countedWordsMap = new HashMap<>();
		countedWordsMap.put(word1.getWord(), word1);
		countedWordsMap.put(word2.getWord(), word2);
		
		List<CountedWord> words = counter.getTop100CountedWords(countedWordsMap);
		assertNotNull(words);
		assertEquals(word2, words.get(0));
		assertEquals(word1, words.get(1));
	}
	
}
