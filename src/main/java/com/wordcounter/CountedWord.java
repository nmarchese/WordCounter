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
		int c = compareToWord.count - this.count;
		if (c == 0) {
			c = this.word.compareTo(compareToWord.word);
		}
		return c;
	}
	
}
