package it.polito.tdp.flight.model;

import java.util.HashMap;

public class AirportIdMap {
	
	private HashMap<Integer, Airport> map;

	public AirportIdMap() {
		this.map = new HashMap<Integer, Airport>();
	}
	
	public Airport get(int id) {
		return this.map.get(id);
	}
	
	public Airport get(Airport airport) {
		Airport old= this.get(airport.getAirportId());
		if (old==null) {
			this.map.put(airport.getAirportId(), airport);
			return airport;
		}
		return old;
		
	}
	

}
