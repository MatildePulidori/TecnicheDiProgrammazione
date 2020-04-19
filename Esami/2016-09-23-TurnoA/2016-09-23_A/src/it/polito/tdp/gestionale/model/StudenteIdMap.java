package it.polito.tdp.gestionale.model;

import java.util.HashMap;

public class StudenteIdMap {

	private HashMap<Integer, Studente> map;
	
	public StudenteIdMap() {
		map = new HashMap<Integer,Studente>();
	}
	

	public Studente get(int matricola) {
		return map.get(matricola);
	}
	
	
	public Studente get(Studente studente) {
		Studente old = this.map.get(studente.getMatricola());
		if (old== null) {
			this.map.put(studente.getMatricola(), studente);
			return studente;
		}
		return old;
	}
	
}
