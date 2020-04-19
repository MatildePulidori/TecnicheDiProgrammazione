package it.polito.tdp.spellchecker.model;

import java.util.*;
import java.io.*;

public class Dictionary {
	
	private List<String> dictionaryItaliano;
	private List<String> dictionaryInglese;
	private List<String> dictionaryCurrent;

	
	public Dictionary() {
		dictionaryItaliano = new ArrayList<String>();
		dictionaryInglese = new ArrayList<String>();;
		dictionaryCurrent = new ArrayList<String>();
		this.loadDictionaryFromFile("rsc/Italian.txt");
		this.loadDictionaryFromFile("rsc/English.txt");
	}
	
	
	public void loadDictionaryFromFile (String nomeFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomeFile));
			String parolaInput="";
			try {
				while ((parolaInput=br.readLine())!=null) {
					if(nomeFile.compareTo("rsc/English.txt")==0) {
						dictionaryInglese.add(parolaInput);
					}
					if (nomeFile.compareTo("rsc/Italian.txt")==0) {
						dictionaryItaliano.add(parolaInput);
					}
				}
				Collections.sort(dictionaryItaliano, new ComparatoreParoleOrdineAlfabetico());
				Collections.sort(dictionaryInglese, new ComparatoreParoleOrdineAlfabetico());
				br.close();
				} catch (IOException e) {
				
			}
		} catch(FileNotFoundException fnfe) {
			
		}
		
		
	}
	
	public void loadDictionary (String language) {
		if(language.equals("Italiano"))
			dictionaryCurrent = dictionaryItaliano;
		else if (language.equals("Inglese"))
			dictionaryCurrent = dictionaryInglese;
		
	}


	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> lista = new ArrayList<RichWord>();
		for(String s : inputTextList) {
			RichWord newWord = new RichWord(s);
			if (this.dictionaryCurrent.contains(s)) {
				newWord.setInDictionary(true);
			}
			lista.add(newWord);
		}
		return lista;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> lista = new ArrayList<RichWord>();
		for (int i=0; i<inputTextList.size(); i++) {
			RichWord newWord = new RichWord(inputTextList.get(i));
			for (int j=0; j<dictionaryCurrent.size(); j++) {
				if (dictionaryCurrent.get(j).toLowerCase().compareTo(newWord.getRichWord())==0) {
					newWord.setInDictionary(true);
					j=dictionaryCurrent.size()-1;
				}
			}
			lista.add(newWord);
		}
		return lista;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> lista = new ArrayList<RichWord>();
		int grandezzaDizionario = dictionaryCurrent.size();
		
		for (String parolaInput : inputTextList) {
			RichWord newWord = new RichWord(parolaInput);
			int fine = grandezzaDizionario;
			int inizio = 0;
			while (inizio!=fine){
				int medio = inizio + (fine - inizio)/2;
				if (newWord.getRichWord().compareToIgnoreCase(dictionaryCurrent.get(medio))==0){
					newWord.setInDictionary(true);
				} 
				else if (newWord.getRichWord().compareToIgnoreCase(dictionaryCurrent.get(medio))>0){
	               inizio=medio +1;
				} 
				else {
	               fine=medio;
	           }
			}
			lista.add(newWord);
		}
		return lista;
	}
	
}
