package com.wordcounter;

import java.util.List;

public class Runner {
	
	public static void main(String[] args) {
		String stopWordsFile = "stop-words.txt";
		String fileToCountFile = "mobydick.txt";
		WordCounter counter = new WordCounter();
		counter.populateStopWordsFromFile(stopWordsFile);
		List<CountedWord> words = counter.getTop100CountedWordsFromFile(fileToCountFile);
		for (CountedWord word : words) {
			System.out.println(word.getWord() + " : " + word.getCount());
		}
	}

}
