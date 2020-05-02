package com.wordcounter;

import java.util.List;

public class Runner {
	
	public static void main(String[] args) {
		if (args.length > 2) {
			throw new IllegalArgumentException("Arguments must be fewer than 2");
		}
		
		WordCounter counter = new WordCounter();
		List<CountedWord> words;
		
		System.out.println("\n\n******************* START ********************\n");
		
		if (args.length > 1) {
			System.out.println("********* Using custom stopWordsFile *********");
			counter.populateStopWordsFromFile(args[1]);
		} else {
			System.out.println("********* Using default stopWordsFile ********");
			counter.populateDefaultStopWords();
		}
		
		if (args.length > 0) {
			System.out.println("********* Using custom fileToCount ***********");
			words = counter.getTop100CountedWordsFromFile(args[0]);
		} else {
			System.out.println("********* Using default fileToCount **********");
			words = counter.getTop100CountedWordsFromDefaultFile();
		}
		
		System.out.println();
		for (CountedWord word : words) {
			System.out.println(word.getWord() + " : " + word.getCount());
		}
	}

}
