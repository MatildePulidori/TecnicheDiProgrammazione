package it.polito.tdp.flightdelays.model;

import java.util.HashMap;
import java.util.Map;

public class FlightIdMap {
	
private Map<Integer,Flight> map;
	
	public FlightIdMap() {
		map = new HashMap<Integer,Flight>();
	}
	
	public Flight getFlight(Integer id) {
		return map.get(id);
	}
	
	public Flight getFlight(Flight flight) {
		Flight old = this.map.get(flight.getId());
			if (old==null) {
				this.map.put(flight.getId(), flight);
				return flight;
			}
		return old;
		
	}


}
