package it.polito.tdp.alien;

public class Word {

	private String alienWord; 
	private String translation;
	

	/**
	 * Costruttore di Word()
	 * @param alienWord
	 * @param translation
	 */
	public Word (String alienWord, String translation) {
		
		this.alienWord=alienWord;
		this.translation=translation;
	}
		
	
	public String getAlienWord() {
		return alienWord;
	}

	public void setAlienWord(String alienWord) {
		this.alienWord = alienWord;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	/**
	 * Controlla se la parola inserita è già presente nel dizionario
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (alienWord == null) {
			if (other.alienWord != null)
				return false;
		} else if (!alienWord.toLowerCase().equals(other.alienWord.toLowerCase()))
			return false;
		if (translation == null) {
			if (other.translation != null)
				return false;
		} else if (!translation.toLowerCase().equals(other.translation.toLowerCase()))
			return false;
		return true;
	}
	
	
}
