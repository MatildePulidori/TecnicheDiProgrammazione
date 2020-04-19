package it.polito.esame.bean;

import java.util.HashMap;
import java.util.Map;

public class CorsoIdMap {
	
private Map<String,Corso> corsi;
	
	public CorsoIdMap() {
		corsi = new HashMap<String,Corso>();
	}
	
	public Corso getCorso(String codins) {
		return corsi.get(codins);
	}
	
	public Corso getCorso(Corso corso) {
		Corso old = corsi.get(corso.getCodins());
		if (old == null) {
			corsi.put(corso.getCodins(), corso);
			return corso;
		}
		return old;
	}
	
	public void put (Corso c) {
		corsi.put(c.getCodins(), c);
	}

}
