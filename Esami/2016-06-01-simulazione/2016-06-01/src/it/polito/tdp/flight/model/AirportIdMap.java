package it.polito.tdp.flight.model;

import java.util.HashMap;

public class AirportIdMap {
	
	private HashMap<Integer, Airport> map;
	
	public AirportIdMap() {
		map = new HashMap<Integer, Airport>();
	}

	public Airport get(int airportId) {
		return this.map.get(airportId);
	}

	public Airport get(Airport airport) {
		Airport old = this.map.get(airport.getAirportId());
		if (old == null) {
			this.map.put(airport.getAirportId(), airport);
			return airport;
		}
		return old;
	}
}
