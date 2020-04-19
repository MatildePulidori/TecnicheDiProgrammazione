package it.polito.tdp.flight.model;
import java.util.*;

public class AirlineIdMap {
	
	private Map<Integer, Airline> map  ;

	public AirlineIdMap () {
		map = new HashMap<>();
	}
	
	
	/**
	 * Dato un Id, mi restituisce l'oggetto Airline corrispondente, se esiste.
	 * Altrimeni ritorna null.
	 * @param airlineId
	 * @return
	 */
	public Airline get(int airlineId) {
		return map.get(airlineId);
	}
	
	
	/**
	 * Dato un oggetto Airline, controllo se è già presente nella mappa.
	 * Se no, aggiunge l'Airline.
	 * @param airline
	 * @return
	 */
	public Airline get(Airline airline) {
		Airline old = map.get(airline.getAirlineId());
		if (old == null) {
			map.put(airline.getAirlineId(),  airline);
			return airline;
		}
		return old;
	}

	public void put(Airline airline, int airlineId) {
		map.put(airlineId, airline);
	}
}
