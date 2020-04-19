package it.polito.tdp.spellchecker.model;

public class RichWord {

	private String richWord;
	private boolean isInDictionary;
	
	public RichWord(String richWord) {
		this.richWord=richWord;
		isInDictionary=false;
	}

	public String getRichWord() {
		return richWord;
	}

	public boolean isInDictionary() {
		return isInDictionary;
	}

	public void setInDictionary(boolean isInDictionary) {
		this.isInDictionary = isInDictionary;
	}

	public void setRichWord(String richWord) {
		this.richWord = richWord;
	}

	
}
