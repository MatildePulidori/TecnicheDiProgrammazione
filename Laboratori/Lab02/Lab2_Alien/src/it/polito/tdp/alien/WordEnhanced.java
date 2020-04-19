package it.polito.tdp.alien;

import java.util.*;

public class WordEnhanced {
	private String alienWord; 
	private List<String> translations;
	

	/**
	 * Costruttore di WordEnhanched()
	 * @param alienWord
	 * @param translation
	 */
	public WordEnhanced (String alienWord, String translation) {
		
		this.alienWord=alienWord;
		translations = new ArrayList<String>();
		translations.add(translation);
	}
		
	/**
	 * Costruttore di WordEnhanched()
	 * @param alienWord
	 */
	public WordEnhanced (String alienWord) {
		
		this.alienWord=alienWord;
		translations = new ArrayList<String>();
	}
		
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<"+alienWord+"> <");
		for (int i=0; i<translations.size(); i++) {
			if (i==translations.size()-1) {
				s.append(translations.get(i)+">\n");		
			}
			else {
				s.append(translations.get(i)+", ");	
			}
		}
		return s.toString();
	}

	public String getAlienWord() {
		return alienWord;
	}

	public void setAlienWord(String alienWord) {
		this.alienWord = alienWord;
	}

	public List<String> getTranslation() {
		return translations;
	}

	public void addTranslation(String translation) {
		if (!translations.contains(translation)) 
			translations.add(translation);
	}


	public boolean equals(WordEnhanced we) {
		if (this.alienWord.toLowerCase().compareTo(we.alienWord.toLowerCase())!=0) {
			return false;
		}
		return true;
	}



}
