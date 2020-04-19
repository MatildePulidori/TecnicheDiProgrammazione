package it.polito.tdp.anagrammi.model;

import java.util.*;

import it.polito.tdp.anagrammi.DAO.AnagrammaDAO;

public class ModelAnagrammi {

	AnagrammaDAO  anagrammaDao;
	
	public ModelAnagrammi() {
		
		anagrammaDao= new AnagrammaDAO();
	}
	
	public Set<String> calcolaAnagrammi(String parola){
		Set<String> anagrammi = new HashSet<String>();
		String parziale = "";
		calcola(parziale,parola,0,anagrammi);
		return anagrammi;
	}

	private void calcola(String parziale, String parola, int passo, Set<String> anagrammi) {
		
		if (passo == parola.length()) {
			anagrammi.add(parziale);
			return;
		}
		
		for(int i = 0; i<parola.length(); i++) {
			if ( (contaCaratteri(parziale, parola.charAt(i))) < (contaCaratteri(parola, parola.charAt(i))) ){
				parziale += parola.charAt(i);
				calcola(parziale, parola, passo+1, anagrammi);
				parziale = parziale.substring(0, parziale.length()-1);
			}	
		}
	}
	
	
	public int contaCaratteri(String stringa, char c ) {
		int contatore = 0 ;
		for (int i=0; i<stringa.length(); i++) {
			if (stringa.charAt(i)==c) {
				contatore++;
			}
		}
		return contatore;
	}
	
	public boolean isCorrect(String anagramma) {
		if (anagrammaDao.isCorrect(anagramma))
			return true;
		else
			return false;
	}
}

