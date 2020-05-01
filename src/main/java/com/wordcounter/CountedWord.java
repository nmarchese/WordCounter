package com.wordcounter;

public class CountedWord implements Comparable<CountedWord> {
	private String word;
	private int count;
	
	public CountedWord(String word) {
		this.word = word;
		count = 1;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getCount() {
		return count;
	}
	
	public void iterateCount() {
		count++;
	}
	
	
	@Override
	public int compareTo(CountedWord compareToWord) {
		return compareToWord.count - this.count;
	}
	
}
