package com.wordcounter;

import java.util.List;

public class Runner {
	
	public static void main(String[] args) {
		String stopWordsPath = "inputFiles\\stop-words.txt";
		String fileToCountPath = "inputFiles\\mobydick.txt";
		WordCounter counter = new WordCounter();
		counter.populateStopWordsFromFile(stopWordsPath);
		List<CountedWord> words = counter.getTop100CountedWordsFromFile(fileToCountPath);
		for (CountedWord word : words) {
			System.out.println(word.getWord() + " : " + word.getCount());
		}
	}

}
