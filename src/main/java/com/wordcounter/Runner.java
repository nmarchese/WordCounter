package com.wordcounter;

import java.util.List;

public class Runner {
	
	public static void main(String[] args) {
		String stopWordsPath = "C:\\Users\\nmarc\\eclipse-workspace\\WordCounter\\inputFiles\\stop-words.txt";
		String fileToCountPath = "C:\\Users\\nmarc\\eclipse-workspace\\WordCounter\\inputFiles\\mobydick.txt";
		WordCounter counter = new WordCounter();
		counter.setStopWordsFromFile(stopWordsPath);
		counter.populateCountedWordsMapFromFile(fileToCountPath);

		List<CountedWord> words = counter.getTop100CountedWords();
		for (CountedWord word : words) {
			System.out.println(word.getWord() + " : " + word.getCount());
		}
	}

}
