package it.polito.esame.bean;

import java.util.HashMap;
import java.util.Map;

public class StudenteIdMap {

	private Map<Integer, Studente> studenti;
	
	public StudenteIdMap() {
		studenti = new HashMap<Integer,Studente>();
	}
	
	public Studente getStudente(int matricola) {
		return studenti.get(matricola);
	}
	
	public Studente getStudente(Studente studente) {
		Studente old = studenti.get(studente.getMatricola());
		if (old == null) {
			studenti.put(studente.getMatricola(), studente);
			return studente;
		}
		return old;
	}
	 
	public void put (Studente studente) {
		studenti.put(studente.getMatricola(), studente);
	}
}
