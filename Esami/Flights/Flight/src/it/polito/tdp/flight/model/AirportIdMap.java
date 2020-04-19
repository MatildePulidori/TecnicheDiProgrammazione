package it.polito.tdp.flight.model;
import java.util.*;

public class AirportIdMap {
	
	private Map<Integer, Airport> map  ;

	public AirportIdMap () {
		map = new HashMap<>();
	}
	
	
	/**
	 * Dato un Id, mi restituisce l'oggetto Airport corrispondente, se esiste.
	 * Altrimeni ritorna null.
	 * @param AirportId
	 * @return
	 */
	public Airport get(int airportId) {
		return map.get(airportId);
	}
	
	
	/**
	 * Dato un oggetto Airport, controllo se è già presente nella mappa.
	 * Se no, aggiunge l'Airport.
	 * @param Airport
	 * @return
	 */
	public Airport get(Airport airport) {
		Airport old = map.get(airport.getAirportId());
		if (old == null) {
			map.put(airport.getAirportId(), airport);
			return airport;
		}
		return old;
	}

	public void put(Airport airport, int airportId) {
		map.put(airportId, airport);
	}
}
