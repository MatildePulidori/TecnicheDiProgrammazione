package it.polito.tdp.gestionale.model;

import java.util.HashMap;

public class CorsoIdMap {
	private HashMap<String, Corso> map;
	
	public CorsoIdMap() {
		map = new HashMap<String,Corso>();
	}
	
	public Corso get(String codins) {
		return map.get(codins);
	}
	
	public Corso get(Corso corso) {
		Corso old = this.map.get(corso.getCodins());
		if (old== null) {
			this.map.put(corso.getCodins(), corso);
			return corso;
		}
		return old;
	}
	
}
