package it.polito.tdp.flightdelays.model;

import java.util.HashMap;
import java.util.Map;

public class AirlineIdMap {

	
	private Map<Integer, Airline> map;
	
	public AirlineIdMap() {
		map = new HashMap<Integer, Airline>();
	}
	
	public Airline getAirline(Integer id) {
		return map.get(id);
	}
	
	public Airline getAirline(Airline airline) {
		Airline old = this.map.get(airline.getId());
			if (old==null) {
				this.map.put(airline.getId(), airline);
				return airline;
			}
		return old;
		
	}
	
}
