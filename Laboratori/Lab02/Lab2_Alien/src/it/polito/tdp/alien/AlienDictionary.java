package it.polito.tdp.alien;

import java.util.*;

public class AlienDictionary {
	
	List<WordEnhanced> words;

	public AlienDictionary() {
		words = new ArrayList<WordEnhanced>();	
	}
	
	public void addWord(String alienWord, String translation) {
		if (words.size()!=0) {
			for (WordEnhanced w : words) {
				if(w.equals(new WordEnhanced(alienWord))) {
					w.addTranslation(translation);
					return;
				}
			}
		}
		WordEnhanced newWord = new WordEnhanced(alienWord, translation);
		words.add(newWord);		
	}
	
	public String translateWord(String alienWord) {
		for (WordEnhanced w : words) {
			if (w.getAlienWord().toLowerCase().compareTo(alienWord.toLowerCase())==0) {
				return w.toString();
			}
		}
		return null;
	}
}
