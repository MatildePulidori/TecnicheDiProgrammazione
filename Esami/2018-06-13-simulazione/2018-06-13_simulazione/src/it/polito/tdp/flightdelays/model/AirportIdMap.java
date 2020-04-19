package it.polito.tdp.flightdelays.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {
	
	private Map<Integer, Airport> map;
	
	public AirportIdMap() {
		map = new HashMap<Integer, Airport>();
	}
	
	public Airport getAirport(Integer id) {
		return map.get(id);
	}
	
	public Airport getAirport(Airport airport) {
		Airport old = this.map.get(airport.getId());
			if (old==null) {
				this.map.put(airport.getId(), airport);
				return airport;
			}
		return old;
		
	}

}
