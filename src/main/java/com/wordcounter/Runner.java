package com.wordcounter;

public class Runner {
	
	public static void main(String[] args) {
		String stopWordsPath = "C:\\Users\\nmarc\\eclipse-workspace\\WordCounter\\inputFiles\\stop-words.txt";
		String fileToCountPath = "C:\\Users\\nmarc\\eclipse-workspace\\WordCounter\\inputFiles\\mobydick.txt";
		WordCounter counter = new WordCounter();
		counter.setStopWordsFromFile(stopWordsPath);
		counter.populateCountByWordsMapFromFile(fileToCountPath);

	}

}
