package it.polito.tdp.flight.model;

import java.util.HashMap;

public class AirlineIdMap {
	private HashMap<Integer,Airline> map;
	
	public AirlineIdMap() {
		map = new HashMap<Integer,Airline>();
	}

	public Airline get(int airlineId) {
		return this.map.get(airlineId);
	}

	public Airline get(Airline airline) {
		Airline old = this.map.get(airline.getAirlineId());
		if (old == null) {
			this.map.put(airline.getAirlineId(), airline);
			return airline;
		}
		return old;
	}
}

