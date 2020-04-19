package it.polito.tdp.esercizioorm.model;
import java.util.*;

public class CorsoIdMap {

	private Map<String, Corso> map ;

	public CorsoIdMap() {
		this.map = new HashMap<>();
	}
	
	
	public Corso get(String codins) {
		return map.get(codins);
	}
	
	// Wrapper di una mappa
	// Quindi bisogna implementare i metodi get e put
	// get = restituisce l'oggetto data la chiave/ il riferimento
	// put = inserisce una nuova coppia chiave-valore
	
	public Corso get(Corso corso) {
		Corso old = map.get(corso.getCodIns());
		if (old==null) {
			// nella mappa non c'è questo corso
			map.put(corso.getCodIns(), corso);
			return corso;
			
		}
		// nella mappa è già stato inserito il corso
		return old;
	}
	
	
	public void put (String codins, Corso corso) {
		map.put(codins, corso);
	}
	
	

}
