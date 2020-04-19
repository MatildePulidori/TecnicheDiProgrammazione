package it.polito.tdp.flight.model;

import java.util.HashMap;

public class AirlineIdMap {
	
	
	private HashMap<Integer, Airline> map;

	public AirlineIdMap() {
		this.map = new HashMap<Integer, Airline>();
	}
	
	public Airline get(int id) {
		return this.map.get(id);
	}
	
	public Airline get(Airline airline) {
		Airline old= this.get(airline.getAirlineId());
		if (old==null) {
			this.map.put(airline.getAirlineId(), airline);
			return airline;
		}
		return old;
		
	}
}
